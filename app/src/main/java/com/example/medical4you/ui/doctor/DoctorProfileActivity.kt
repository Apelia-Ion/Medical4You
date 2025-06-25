package com.example.medical4you.ui.doctor

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.medical4you.R
import com.example.medical4you.data.MedicalAppDatabase
import kotlinx.coroutines.launch
import android.content.Context
import android.content.Intent
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DoctorProfileActivity : AppCompatActivity() {

    private lateinit var tvDoctorName: TextView
    private lateinit var tvSpecialization: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvServices: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profile)

        tvDoctorName = findViewById(R.id.tv_doctor_name)
        tvSpecialization = findViewById(R.id.tv_specialization)
        tvLocation = findViewById(R.id.tv_location)
        tvServices = findViewById(R.id.tv_services)

        findViewById<Button>(R.id.btn_edit_profile).setOnClickListener {
            startActivity(Intent(this, EditDoctorProfileActivity::class.java))
        }

        loadDoctorData()
    }

    override fun onResume() {
        super.onResume()
        loadDoctorData()
    }

    private fun loadDoctorData() {
        val sharedPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val doctorId = sharedPrefs.getInt("doctor_id", -1)

        if (doctorId == -1) {
            Toast.makeText(this, "No doctor info found.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val db = MedicalAppDatabase.getDatabase(this)
        val doctorDao = db.doctorDao()

        lifecycleScope.launch {
            val doctor = withContext(Dispatchers.IO) {
                doctorDao.getDoctorById(doctorId)
            }

            if (doctor != null) {
                tvDoctorName.text = "Name: ${doctor.name}"
                tvSpecialization.text = "Specialization: ${doctor.specialization}"
                tvLocation.text = "Location: ${doctor.location}"

                val formattedServices = doctor.services
                    .split(",")
                    .joinToString("\n") { "• ${it.trim()}" }
                tvServices.text = "Services:\n$formattedServices"
            } else {
                Toast.makeText(this@DoctorProfileActivity, "Doctor not found in database.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}