package com.example.medical4you

import android.app.Application
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicationController : Application() {
    override fun onCreate() {
        super.onCreate()

        val db = MedicalAppDatabase.getDatabase(this)
        val userDao = db.userDao()

        CoroutineScope(Dispatchers.IO).launch {
            val existingAdmin = userDao.getUserByUsername("admin")
            if (existingAdmin == null) {
                val adminUser = User(
                    username = "admin",
                    password = "Admin123", // sau hash-uit, dacă vrei
                    userType = "admin",
                    email = "admin@medical4you.com",
                    name = "Administrator",
                    specialization = null,
                    location = null,
                    schedule = null,
                    services = null
                )
                userDao.insertUser(adminUser)
            }
        }

    }
}