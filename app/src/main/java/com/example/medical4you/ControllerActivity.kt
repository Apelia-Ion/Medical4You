package com.example.medical4you.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.medical4you.MainActivity
import com.example.medical4you.R

class ControllerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controller)

        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userType = prefs.getString("user_type", "pacient") ?: "pacient"

        val title = findViewById<TextView>(R.id.controller_title)
        val btnLogout = findViewById<Button>(R.id.btn_logout)

        val btnDoctorProfile = findViewById<Button>(R.id.btn_doctor_profile)
        val btnDoctorAppointments = findViewById<Button>(R.id.btn_doctor_appointments)
        val btnSearchDoctor = findViewById<Button>(R.id.btn_search_doctor)
        val btnViewAppointments = findViewById<Button>(R.id.btn_view_appointments)
        val btnListPatients = findViewById<Button>(R.id.btn_list_patients)
        val btnListDoctors = findViewById<Button>(R.id.btn_list_doctors)
        val btnPendingDoctors = findViewById<Button>(R.id.btn_pending_doctors)

        when (userType) {
            "doctor" -> {
                title.text = "Meniu Doctor"
                btnDoctorProfile.visibility = View.VISIBLE
                btnDoctorAppointments.visibility = View.VISIBLE
            }
            "pacient" -> {
                title.text = "Meniu Pacient"
                btnSearchDoctor.visibility = View.VISIBLE
                btnViewAppointments.visibility = View.VISIBLE
            }
            "admin" -> {
                title.text = "Meniu Administrator"
                btnListPatients.visibility = View.VISIBLE
                btnListDoctors.visibility = View.VISIBLE
                btnPendingDoctors.visibility = View.VISIBLE
            }
        }

        btnLogout.setOnClickListener {
            btnLogout.isEnabled = false  // ca să nu se apese din nou

            Handler(Looper.getMainLooper()).postDelayed({
                prefs.edit().clear().apply()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }, 150) // 150ms e suficient ca să vezi roșul
        }
    }
}
