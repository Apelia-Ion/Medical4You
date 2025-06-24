package com.example.medical4you

import android.app.Application
import android.content.Context
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.utils.Seeder

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicationController : Application() {
    override fun onCreate() {
        super.onCreate()

        val sharedPref = getSharedPreferences("medical_prefs", Context.MODE_PRIVATE)
        val wasSeeded = sharedPref.getBoolean("was_seeded", false)

        if (!wasSeeded) {
            CoroutineScope(Dispatchers.IO).launch {
                Seeder.seedAllUsers(this@ApplicationController)

                // salvam flag-ul ca sa nu mai inseram din nou la urmatorul start
                sharedPref.edit().putBoolean("was_seeded", true).apply()
            }
        }
    }
}
