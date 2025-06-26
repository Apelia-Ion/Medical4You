package com.example.medical4you.ui.doctor

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medical4you.R
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.data.model.AppointmentWithPatient
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DoctorAppointmentsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DoctorAppointmentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_appointments)

        recyclerView = findViewById(R.id.rv_appointments)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val sharedPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        val doctorId = sharedPrefs.getInt("doctor_id", -1)
        Toast.makeText(this, "Doctor ID: $doctorId", Toast.LENGTH_SHORT).show()
        Log.d("DoctorAppointments", "Doctor ID=$doctorId")


        if (doctorId == -1) {
            Toast.makeText(this, "Doctor ID not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val db = MedicalAppDatabase.getDatabase(this)
        val appointmentDao = db.appointmentDao()

        lifecycleScope.launch {
            val appointments = withContext(Dispatchers.IO) {
                appointmentDao.getAppointmentsWithPatientByDoctorId(doctorId)
            }

            // Aici suntem în Main thread din nou
            adapter = DoctorAppointmentsAdapter(this@DoctorAppointmentsActivity, appointments)
            recyclerView.adapter = adapter
        }
    }
}