package com.example.medical4you.ui.doctor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.medical4you.R
import com.example.medical4you.ui.auth.LoginActivity

class DoctorMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_menu)

        val btnProfile = findViewById<Button>(R.id.btn_doctor_profile)
        val btnAppointments = findViewById<Button>(R.id.btn_doctor_appointments)
        val btnLogout = findViewById<Button>(R.id.btn_logout)

        btnProfile.setOnClickListener {
            val intent = Intent(this, DoctorProfileActivity::class.java)
            startActivity(intent)
        }

        btnAppointments.setOnClickListener {
            val intent = Intent(this, DoctorAppointmentsActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            getSharedPreferences("user_prefs", MODE_PRIVATE).edit().clear().apply()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}