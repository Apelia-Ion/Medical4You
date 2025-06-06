package com.example.medical4you

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.medical4you.ui.appointments.AppointmentActivity
import com.example.medical4you.ui.auth.LoginActivity

class ControllerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controller)

        val btnAppointments = findViewById<Button>(R.id.btn_open_appointments)
        val btnLogin = findViewById<Button>(R.id.btn_open_login)

        btnAppointments.setOnClickListener {
            val intent = Intent(this, AppointmentActivity::class.java)
            intent.putExtra("userId", 3)
            intent.putExtra("isDoctor", true)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}