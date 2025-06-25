package com.example.medical4you.ui.appointments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.medical4you.R
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.data.model.Appointment
import kotlinx.coroutines.launch
import java.util.*
import kotlinx.coroutines.Dispatchers
import com.example.medical4you.data.dao.AppointmentDao
import kotlinx.coroutines.withContext


class ScheduleAppointmentActivity : AppCompatActivity()  {

    private lateinit var tvDoctorName: TextView
    private lateinit var etDate: EditText
    private lateinit var etTime: EditText
    private lateinit var btnConfirm: Button

    private var selectedDate = ""
    private var selectedTime = ""
    private var doctorId = -1
    private var patientId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_appointment)

        tvDoctorName = findViewById(R.id.tv_doctor_name)
        etDate = findViewById(R.id.et_date)
        etTime = findViewById(R.id.et_time)
        btnConfirm = findViewById(R.id.btn_confirm)

        val sharedPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        patientId = sharedPrefs.getInt("user_id", -1)
        val userType = sharedPrefs.getString("user_type", "")

        doctorId = intent.getIntExtra("doctor_id", -1)
        val doctorName = intent.getStringExtra("doctor_name") ?: "Selected Doctor"

        if (patientId == -1 || doctorId == -1 || userType != "pacient") {
            Toast.makeText(this, "Authentication error. Please login as a patient.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        tvDoctorName.text = "Schedule with Dr. $doctorName"

        etDate.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    selectedDate = "%02d/%02d/%04d".format(day, month + 1, year)
                    etDate.setText(selectedDate)
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        etTime.setOnClickListener {
            val cal = Calendar.getInstance()
            TimePickerDialog(
                this,
                { _, hour, minute ->
                    selectedTime = "%02d:%02d".format(hour, minute)
                    etTime.setText(selectedTime)
                },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        btnConfirm.setOnClickListener {
            if (selectedDate.isBlank() || selectedTime.isBlank()) {
                Toast.makeText(this, "Please select both date and time.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dateTime = "$selectedDate $selectedTime"
            val appointment = Appointment(
                patientId = patientId,
                doctorId = doctorId,
                dateTime = dateTime,
                status = "Pending"
            )

            lifecycleScope.launch {
                val db = MedicalAppDatabase.getDatabase(this@ScheduleAppointmentActivity)

                withContext(Dispatchers.IO) {
                    db.appointmentDao().insertAppointment(appointment)
                }

                Toast.makeText(this@ScheduleAppointmentActivity, "Appointment requested!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}