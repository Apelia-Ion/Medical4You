package com.example.medical4you.ui.auth

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.medical4you.R
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.data.dao.*
import com.example.medical4you.data.model.*
import kotlinx.coroutines.launch
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var userDao: UserDao
    private lateinit var doctorDao: DoctorDao
    private lateinit var patientDao: PatientDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val db = MedicalAppDatabase.getDatabase(this)
        userDao = db.userDao()
        doctorDao = db.doctorDao()
        patientDao = db.patientDao()

        val etUsername = findViewById<EditText>(R.id.et_username)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val etEmail = findViewById<EditText>(R.id.et_email)

        val cbDoctor = findViewById<CheckBox>(R.id.cb_doctor)
        val cbPatient = findViewById<CheckBox>(R.id.cb_patient)
        val tvDoctorNote = findViewById<TextView>(R.id.tv_doctor_note)

        val doctorFields = findViewById<LinearLayout>(R.id.layout_doctor_fields)
        val patientFields = findViewById<LinearLayout>(R.id.layout_patient_fields)

        val etDoctorName = findViewById<EditText>(R.id.et_doctor_name)
        val spSpecialization = findViewById<Spinner>(R.id.sp_specialization)
        val spLocation = findViewById<Spinner>(R.id.spinner_location)
        val etSchedule = findViewById<EditText>(R.id.et_schedule)
        val spServices = findViewById<Spinner>(R.id.sp_services)

        val etPatientName = findViewById<EditText>(R.id.et_patient_name)
        val etCnp = findViewById<EditText>(R.id.et_cnp)
        val etDateOfBirth = findViewById<EditText>(R.id.et_date_of_birth)
        val etInsuranceNumber = findViewById<EditText>(R.id.et_insurance_number)

        val btnRegister = findViewById<Button>(R.id.btn_register)
        val btnGoToLogin = findViewById<Button>(R.id.btn_go_to_login)

        spSpecialization.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item,
            listOf("Cardiology", "Dermatology", "Neurology", "Pediatrics", "Psychiatry")
        )

        spLocation.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item,
            listOf("Bucharest", "Cluj", "Iasi", "Timisoara", "Constanta")
        )

        spServices.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item,
            listOf("Consultation", "Follow-up", "Vaccination", "Treatment", "Emergency")
        )

        cbDoctor.setOnCheckedChangeListener { _, isChecked ->
            doctorFields.visibility = if (isChecked) View.VISIBLE else View.GONE
            tvDoctorNote.visibility = if (isChecked) View.VISIBLE else View.GONE
            if (isChecked) cbPatient.isChecked = false
        }

        cbPatient.setOnCheckedChangeListener { _, isChecked ->
            patientFields.visibility = if (isChecked) View.VISIBLE else View.GONE
            if (isChecked) {
                cbDoctor.isChecked = false
                tvDoctorNote.visibility = View.GONE
            }
        }

        etDateOfBirth.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, y, m, d ->
                etDateOfBirth.setText("$d/${m + 1}/$y")
            }, year, month, day).show()
        }

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString()
            val email = etEmail.text.toString().trim()

            if (username.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Username and email are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6 || !password.any { it.isDigit() } || !password.any { it.isUpperCase() }) {
                Toast.makeText(this, "Password must be at least 6 characters long, include a digit and an uppercase letter", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val userType = when {
                cbDoctor.isChecked -> "doctor"
                cbPatient.isChecked -> "pacient"
                else -> {
                    Toast.makeText(this, "Select an account type", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            lifecycleScope.launch {
                val existing = userDao.getUserByUsername(username)
                if (existing != null) {
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Username already taken", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val user = User(
                        username = username,
                        password = password,
                        email = email,
                        userType = userType,
                        name = username,
                        specialization = null,
                        location = null,
                        schedule = null,
                        services = null
                    )
                    val userId = userDao.insertUser(user).toInt()

                    if (userType == "doctor") {
                        val name = etDoctorName.text.toString().trim()
                        val schedule = etSchedule.text.toString().trim()
                        val specialization = spSpecialization.selectedItem.toString()
                        val location = spLocation.selectedItem.toString()
                        val services = spServices.selectedItem.toString()

                        if (name.isBlank() || schedule.isBlank()) {
                            runOnUiThread {
                                Toast.makeText(this@RegisterActivity, "Please complete all doctor fields", Toast.LENGTH_SHORT).show()
                            }
                            return@launch
                        }

                        val doctor = Doctor(
                            userId = userId,
                            name = name,
                            specialization = specialization,
                            location = location,
                            schedule = schedule,
                            services = services
                        )
                        doctorDao.insertDoctor(doctor)

                    } else {
                        val name = etPatientName.text.toString().trim()
                        val cnp = etCnp.text.toString().trim()
                        val dob = etDateOfBirth.text.toString().trim()
                        val insurance = etInsuranceNumber.text.toString().trim()

                        val dateRegex = Regex("""\d{1,2}/\d{1,2}/\d{4}""")

                        if (name.isBlank() || cnp.length != 13 || !cnp.all { it.isDigit() } ||
                            dob.isBlank() || !dob.matches(dateRegex) || insurance.isBlank()) {
                            runOnUiThread {
                                Toast.makeText(this@RegisterActivity, "Please complete all patient fields correctly", Toast.LENGTH_SHORT).show()
                            }
                            return@launch
                        }

                        val patient = Patient(
                            userId = userId,
                            cnp = cnp,
                            dateOfBirth = dob,
                            insuranceNumber = insurance
                        )
                        patientDao.insertPatient(patient)
                    }

                    runOnUiThread {
                        val message = when (userType) {
                            "doctor" -> "Welcome, doctor! Your account is pending admin approval."
                            "pacient" -> "Welcome aboard! Your patient account has been created successfully."
                            else -> "Registration complete!"
                        }

                        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        }

        btnGoToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}