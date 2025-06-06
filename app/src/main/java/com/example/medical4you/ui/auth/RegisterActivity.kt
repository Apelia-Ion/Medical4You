package com.example.medical4you.ui.auth

import android.R.attr.name
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.medical4you.R
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.data.dao.UserDao
import com.example.medical4you.data.model.User
import kotlinx.coroutines.launch


class RegisterActivity : AppCompatActivity(){

    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inițializăm baza de date și dao
        val db = MedicalAppDatabase.getDatabase(this)
        userDao = db.userDao()

        val btnRegister = findViewById<Button>(R.id.btn_register)
        val etUsername = findViewById<EditText>(R.id.et_username)
        val etPassword = findViewById<EditText>(R.id.et_password)

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString()

            if (username.isEmpty() || password.length < 4) {
                Toast.makeText(this, "Username gol sau parolă prea scurtă", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

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
                        userType = "pacient",
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
    }
}