package com.example.medical4you.ui.appointments

import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.medical4you.R
import com.example.medical4you.data.MedicalAppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PatientAppointmentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_appointments)

        val userId = getSharedPreferences("user_prefs", Context.MODE_PRIVATE).getInt("user_id", -1)
        if (userId == -1) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val layout = findViewById<LinearLayout>(R.id.appointment_list)

        val db = MedicalAppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val appointments = withContext(Dispatchers.IO) {
                db.appointmentDao().getAppointmentsByPatientId(userId)
            }

            if (appointments.isEmpty()) {
                runOnUiThread {
                    layout.addView(TextView(this@PatientAppointmentsActivity).apply {
                        text = "You have no appointments yet."
                        textSize = 16f
                    })
                }
            } else {
                appointments.forEach { appt ->
                    val doctor = withContext(Dispatchers.IO) {
                        db.doctorDao().getDoctorByUserId(appt.doctorId)
                    }

                    runOnUiThread {
                        val tv = TextView(this@PatientAppointmentsActivity).apply {
                            text = """
                                Doctor: ${doctor?.name ?: "Unknown"}
                                Date: ${appt.dateTime}
                                Status: ${appt.status}
                            """.trimIndent()
                            textSize = 16f
                            setPadding(16, 16, 16, 16)
                        }
                        layout.addView(tv)
                    }
                }
            }
        }
    }
}