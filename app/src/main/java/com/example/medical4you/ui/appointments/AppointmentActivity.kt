package com.example.medical4you.ui.appointments


import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medical4you.R
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.data.repositories.AppointmentRepository
import com.example.medical4you.data.viewmodel.AppointmentViewModel

import com.example.medical4you.viewmodel.factory.AppointmentViewModelFactory

class AppointmentActivity : AppCompatActivity() {

    private lateinit var viewModel: AppointmentViewModel
    private lateinit var adapter: AppointmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_patient_appointment)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_appointments)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = AppointmentAdapter(emptyList())
        recyclerView.adapter = adapter

        val db = MedicalAppDatabase.getDatabase(this)
        val repository = AppointmentRepository(db.appointmentDao())
        val factory = AppointmentViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[AppointmentViewModel::class.java]

        // Folosim SharedPreferences pentru a obține user_id
        val userId = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            .getInt("user_id", -1)

        if (userId != -1) {
            viewModel.getAppointmentsWithDoctor(userId)
            viewModel.appointmentsWithDoctor.observe(this) {
                adapter.updateAppointments(it)
            }
        }
    }
}