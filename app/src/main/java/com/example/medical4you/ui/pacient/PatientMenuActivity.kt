package com.example.medical4you.ui.pacient

import androidx.fragment.app.commit
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.medical4you.R
import com.example.medical4you.ui.auth.LoginActivity
import com.example.medical4you.ui.pacient.DoctorSearchFragment
import com.example.medical4you.ui.pacient.PatientAppointmentFragment

class PatientMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_menu)

        val btnSearchDoctor = findViewById<Button>(R.id.btn_search_doctor)
        val btnAppointments = findViewById<Button>(R.id.btn_view_appointments)
        val btnLogout = findViewById<Button>(R.id.btn_logout)
        val menuLayout = findViewById<LinearLayout>(R.id.menu_container)

        btnSearchDoctor.setOnClickListener {
            menuLayout.visibility = View.GONE
            supportFragmentManager.commit {
                replace(R.id.fragment_container, DoctorSearchFragment())
                addToBackStack(null)
            }
        }

        btnAppointments.setOnClickListener {
            menuLayout.visibility = View.GONE
            supportFragmentManager.commit {
                replace(R.id.fragment_container, PatientAppointmentFragment())
                addToBackStack(null)
            }
        }

        btnLogout.setOnClickListener {
            getSharedPreferences("user_prefs", Context.MODE_PRIVATE).edit().clear().apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            findViewById<LinearLayout>(R.id.menu_container).visibility = View.VISIBLE
        } else {
            super.onBackPressed()
        }
    }
}
