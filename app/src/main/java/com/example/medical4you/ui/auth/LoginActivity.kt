package com.example.medical4you.ui.auth

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.medical4you.ui.ControllerActivity
import com.example.medical4you.R
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.data.dao.UserDao
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var userDao: UserDao
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val db = MedicalAppDatabase.getDatabase(this)
        userDao = db.userDao()
        sharedPrefs = getSharedPreferences("user_prefs", MODE_PRIVATE)

        if (sharedPrefs.contains("user_id")) {
            goToHome()
            return
        }

        val etUsername = findViewById<EditText>(R.id.et_username)
        val etPassword = findViewById<EditText>(R.id.et_password)

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val user = userDao.login(username, password)

                if (user != null) {
                    val doctorDao = db.doctorDao()
                    val doctor = if (user.userType == "doctor") doctorDao.getDoctorByUserId(user.userId) else null
                    val doctorId = doctor?.userId

                    saveLogin(user.userId, user.userType.lowercase(), doctorId)

                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                        goToHome()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "Username and/or password are incorrect.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        findViewById<Button>(R.id.btn_go_to_register).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun saveLogin(userId: Int, userType: String, doctorId: Int?) {
        with(sharedPrefs.edit()) {
            putInt("user_id", userId)
            putString("user_type", userType)
            if (userType == "doctor" && doctorId != null) {
                putInt("doctor_id", doctorId)
            } else {
                remove("doctor_id")
            }
            apply()
        }
    }

    private fun goToHome() {
        startActivity(Intent(this, ControllerActivity::class.java))
        finish()
    }
}