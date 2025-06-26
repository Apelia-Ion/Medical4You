package com.example.medical4you.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.medical4you.MainActivity
import com.example.medical4you.R
import com.example.medical4you.ui.admin.AdminDoctorListFragment
import com.example.medical4you.ui.admin.AdminPatientListFragment
import com.example.medical4you.ui.admin.AdminPendingDoctorListFragment
import com.example.medical4you.ui.doctor.DoctorProfileActivity
import com.example.medical4you.ui.doctor.DoctorAppointmentsActivity
import com.example.medical4you.ui.pacient.PatientAppointmentFragment
import com.example.medical4you.ui.pacient.DoctorSearchFragment
import androidx.fragment.app.Fragment
import com.example.medical4you.ui.pacient.NewsFragment

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
        val btnNews = findViewById<Button>(R.id.btn_news)
        val btnListPatients = findViewById<Button>(R.id.btn_list_patients)
        val btnListDoctors = findViewById<Button>(R.id.btn_list_doctors)
        val btnPendingDoctors = findViewById<Button>(R.id.btn_pending_doctors)


        val menuContainer = findViewById<LinearLayout>(R.id.menu_container)

        when (userType) {
            "doctor" -> {
                title.text = "Meniu Doctor"
                btnDoctorProfile.visibility = View.VISIBLE
                btnDoctorAppointments.visibility = View.VISIBLE

                btnDoctorProfile.setOnClickListener {
                    startActivity(Intent(this, DoctorProfileActivity::class.java))
                }

                btnDoctorAppointments.setOnClickListener {
                    startActivity(Intent(this, DoctorAppointmentsActivity::class.java))
                }
            }
            "pacient" -> {
                title.text = "Meniu Pacient"
                btnSearchDoctor.visibility = View.VISIBLE
                btnViewAppointments.visibility = View.VISIBLE

                btnSearchDoctor.setOnClickListener {
                    showFragment(DoctorSearchFragment())
                }

                btnViewAppointments.setOnClickListener {
                    showFragment(PatientAppointmentFragment())
                }

                btnNews.visibility = View.VISIBLE
                btnNews.setOnClickListener {
                    showFragment(NewsFragment())
                }
            }
            "admin" -> {
                title.text = "Meniu Administrator"
                btnListPatients.visibility = View.VISIBLE
                btnListDoctors.visibility = View.VISIBLE
                btnPendingDoctors.visibility = View.VISIBLE
            }
        }

        btnLogout.setOnClickListener {
            btnLogout.isEnabled = false

            Handler(Looper.getMainLooper()).postDelayed({
                prefs.edit().clear().apply()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }, 150)
        }

        // Buton DOCTORS → fragment full-screen + ascunde meniu
        btnListDoctors.setOnClickListener {
            menuContainer.visibility = View.GONE
            btnLogout.visibility = View.GONE

            supportFragmentManager.beginTransaction()
                .replace(R.id.main_controller, AdminDoctorListFragment())
                .commit()
        }

        btnPendingDoctors.setOnClickListener {
            menuContainer.visibility = View.GONE
            btnLogout.visibility = View.GONE

            supportFragmentManager.beginTransaction()
                .replace(R.id.main_controller, AdminPendingDoctorListFragment())
                .commit()
        }

        btnListPatients.setOnClickListener {
            menuContainer.visibility = View.GONE
            btnLogout.visibility = View.GONE

            supportFragmentManager.beginTransaction()
                .replace(R.id.main_controller, AdminPatientListFragment())
                .commit()
        }

        btnSearchDoctor.setOnClickListener {
            menuContainer.visibility = View.GONE
            btnLogout.visibility = View.GONE

            supportFragmentManager.beginTransaction()
                .replace(R.id.main_controller, DoctorSearchFragment())
                .addToBackStack(null)
                .commit()
        }

        btnViewAppointments.setOnClickListener {
            menuContainer.visibility = View.GONE
            btnLogout.visibility = View.GONE

            supportFragmentManager.beginTransaction()
                .replace(R.id.main_controller, PatientAppointmentFragment())
                .addToBackStack(null)
                .commit()
        }

        //la apasarea back -> readuce meniul
        supportFragmentManager.addOnBackStackChangedListener {
            val activeFragments = supportFragmentManager.fragments.filter { it.isVisible }

            val menu = findViewById<LinearLayout>(R.id.menu_container)
            val logoutBtn = findViewById<Button>(R.id.btn_logout)

            if (activeFragments.isEmpty()) {
                menu.visibility = View.VISIBLE
                logoutBtn.visibility = View.VISIBLE
            } else {
                menu.visibility = View.GONE
                logoutBtn.visibility = View.GONE
            }
        }

    }

    override fun onResume() {
        super.onResume()

        val activeFragments = supportFragmentManager.fragments.filter { it.isVisible }
        val menu = findViewById<LinearLayout>(R.id.menu_container)
        val logoutBtn = findViewById<Button>(R.id.btn_logout)

        if (activeFragments.isEmpty()) {
            menu.visibility = View.VISIBLE
            logoutBtn.visibility = View.VISIBLE
        } else {
            menu.visibility = View.GONE
            logoutBtn.visibility = View.GONE
        }
    }


    private fun showFragment(fragment: Fragment) {
        findViewById<LinearLayout>(R.id.menu_container).visibility = View.GONE
        findViewById<Button>(R.id.btn_logout).visibility = View.GONE

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_controller, fragment)
            .addToBackStack(null)
            .commit()
    }
}
