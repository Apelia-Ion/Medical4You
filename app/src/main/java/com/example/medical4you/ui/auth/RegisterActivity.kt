package com.example.medical4you.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.medical4you.R
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.data.dao.UserDao
import com.example.medical4you.data.model.User
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val db = MedicalAppDatabase.getDatabase(this)
        userDao = db.userDao()

        val btnRegister = findViewById<Button>(R.id.btn_register)
        val btnGoToLogin = findViewById<Button>(R.id.btn_go_to_login)
        val etUsername = findViewById<EditText>(R.id.et_username)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val etEmail = findViewById<EditText>(R.id.et_email)

        val cbDoctor = findViewById<CheckBox>(R.id.cb_doctor)
        val cbPatient = findViewById<CheckBox>(R.id.cb_patient)
        val tvDoctorNote = findViewById<TextView>(R.id.tv_doctor_note)

        // Logică checkbox: doctor/pacient
        cbDoctor.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tvDoctorNote.visibility = View.VISIBLE
                cbPatient.isChecked = false
            } else {
                tvDoctorNote.visibility = View.GONE
            }
        }

        cbPatient.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                cbDoctor.isChecked = false
                tvDoctorNote.visibility = View.GONE
            }
        }

        // Click pe butonul de înregistrare
        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString()
            val email = etEmail.text.toString().trim()

            // ✅ Validare username
            if (username.isEmpty()) {
                Toast.makeText(this, "Username-ul nu poate fi gol", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ✅ Validare email
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email invalid. Format corect: exemplu@email.com", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ✅ Validare parolă
            val passwordValid = password.length >= 6 &&
                    password.any { it.isDigit() } &&
                    password.any { it.isUpperCase() }

            if (!passwordValid) {
                Toast.makeText(this, "Parola trebuie să aibă minim 6 caractere, o literă mare și o cifră", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // ✅ Determinare tip utilizator
            val userType = when {
                cbDoctor.isChecked -> "doctor"
                cbPatient.isChecked -> "pacient"
                else -> {
                    Toast.makeText(this, "Selectează tipul de utilizator", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            // ✅ Verificare dacă username-ul există și inserare
            lifecycleScope.launch {
                val existingUser = userDao.getUserByUsername(username)
                if (existingUser != null) {
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Username deja folosit", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val newUser = User(
                        username = username,
                        password = password,
                        userType = userType,
                        email = email,
                        name = "",
                        specialization = null,
                        location = null,
                        schedule = null,
                        services = null
                    )

                    userDao.insertUser(newUser)

                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Înregistrare reușită!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }

        btnGoToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
