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
        

    }
}