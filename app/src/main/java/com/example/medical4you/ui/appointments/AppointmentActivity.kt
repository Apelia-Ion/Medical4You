package com.example.medical4you.ui.appointments

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medical4you.R
import com.example.medical4you.viewmodel.factory.AppointmentViewModelFactory
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.data.repositories.AppointmentRepository
import androidx.recyclerview.widget.RecyclerView
import com.example.medical4you.data.viewmodel.AppointmentViewModel

class AppointmentActivity : AppCompatActivity() {

    private lateinit var adapter: AppointmentAdapter
    private lateinit var recyclerView: RecyclerView

    private val viewModel: AppointmentViewModel by viewModels {
        val db = MedicalAppDatabase.getDatabase(applicationContext)
        AppointmentViewModelFactory(AppointmentRepository(db.appointmentDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)

        recyclerView = findViewById(R.id.recyclerAppointments)
        adapter = AppointmentAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val userId = intent.getIntExtra("userId", 0)
        val isDoctor = intent.getBooleanExtra("isDoctor", false)

        if (isDoctor) viewModel.loadAppointmentsForDoctor(userId)
        else viewModel.loadAppointmentsForPatient(userId)

        viewModel.appointments.observe(this) { list ->
            adapter.submitList(list)
        }
    }
}