package com.example.medical4you.ui.appointments


import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.medical4you.R
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.data.model.Appointment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ScheduleAppointmentActivity : AppCompatActivity() {

    private lateinit var tvDoctorName: TextView
    private lateinit var etDate: EditText
    private lateinit var etTime: EditText
    private lateinit var btnConfirm: Button

    private var selectedDate = ""
    private var selectedTime = ""
    private var doctorId = -1
    private var patientId = -1
    private var scheduleJson: JSONObject? = null
    private lateinit var db: MedicalAppDatabase

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

        db = MedicalAppDatabase.getDatabase(this)
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val doctor = db.doctorDao().getDoctorByUserId(doctorId)
                    if (doctor?.schedule.isNullOrBlank()) {
                        runOnUiThread {
                            Toast.makeText(this@ScheduleAppointmentActivity, "Doctor has no schedule set.", Toast.LENGTH_LONG).show()
                            finish()
                        }
                    } else {
                        scheduleJson = JSONObject(doctor!!.schedule)
                    }
                } catch (e: Exception) {
                    Log.e("Schedule", "Error loading schedule: ${e.message}")
                    runOnUiThread {
                        Toast.makeText(this@ScheduleAppointmentActivity, "Error loading doctor schedule.", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }
        }

        etDate.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    val pickedCal = Calendar.getInstance().apply {
                        set(year, month, day, 0, 0, 0)
                        set(Calendar.MILLISECOND, 0)
                    }

                    val today = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }

                    if (pickedCal.before(today)) {
                        Toast.makeText(this, "Please select a future date", Toast.LENGTH_SHORT).show()
                        return@DatePickerDialog
                    }

                    selectedDate = "%02d/%02d/%04d".format(day, month + 1, year)
                    etDate.setText(selectedDate)
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        etTime.setOnClickListener {
            if (selectedDate.isBlank()) {
                Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = sdf.parse(selectedDate) ?: return@setOnClickListener
            val cal = Calendar.getInstance().apply { time = date }
            val weekday = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH)?.lowercase() ?: return@setOnClickListener

            val hours = scheduleJson?.optJSONArray(weekday)
            if (hours == null || hours.length() == 0) {
                Toast.makeText(this, "No available slots for this day", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val hourList = (0 until hours.length()).map { hours.getString(it) }.toTypedArray()

            AlertDialog.Builder(this)
                .setTitle("Select time")
                .setItems(hourList) { _, which ->
                    selectedTime = hourList[which]
                    etTime.setText(selectedTime)
                }
                .show()
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
                withContext(Dispatchers.IO) {
                    db.appointmentDao().insertAppointment(appointment)
                }
                runOnUiThread {
                    Toast.makeText(this@ScheduleAppointmentActivity, "Appointment requested!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
