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
import com.example.medical4you.data.model.User
import kotlinx.coroutines.launch



class LoginActivity : AppCompatActivity(){

    private lateinit var userDao: UserDao
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        // Inițializări
        val db = MedicalAppDatabase.getDatabase(this)
        userDao = db.userDao()
        sharedPrefs = getSharedPreferences("user_prefs", MODE_PRIVATE)

        // Verifică dacă există user deja logat
        if (sharedPrefs.contains("user_id")) {
            goToHome()
            return
        }

        // Buton login
        findViewById<Button>(R.id.btn_login).setOnClickListener {
            val username = findViewById<EditText>(R.id.et_username).text.toString().trim()
            val password = findViewById<EditText>(R.id.et_password).text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out all forms", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val user = userDao.login(username, password)
                if (user != null) {
                    saveLogin(user.userId, user.userType)
                    goToHome()
                } else {
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "Username and/or pasword are incorrect.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Navigare către inregistrar
        findViewById<Button?>(R.id.btn_go_to_register)?.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun saveLogin(userId: Int, userType: String) {
        sharedPrefs.edit()
            .putInt("user_id", userId)
            .putString("user_type", userType)
            .apply()
    }

    private fun goToHome() {
        startActivity(Intent(this, ControllerActivity::class.java))
        finish()
    }
}