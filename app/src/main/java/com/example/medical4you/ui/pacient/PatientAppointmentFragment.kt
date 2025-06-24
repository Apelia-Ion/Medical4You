package com.example.medical4you.ui.pacient

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medical4you.R
import com.example.medical4you.adapter.AppointmentAdapter
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.data.repositories.AppointmentRepository
import com.example.medical4you.data.viewmodel.AppointmentViewModel
import com.example.medical4you.viewmodel.factory.AppointmentViewModelFactory

class PatientAppointmentFragment : Fragment() {

    private lateinit var viewModel: AppointmentViewModel
    private lateinit var adapter: AppointmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_patient_appointment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_appointments)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = AppointmentAdapter(emptyList())
        recyclerView.adapter = adapter

        val db = MedicalAppDatabase.getDatabase(requireContext())
        val repository = AppointmentRepository(db.appointmentDao())
        val factory = AppointmentViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[AppointmentViewModel::class.java]

        // 📥 Extrage ID-ul pacientului din SharedPreferences
        val sharedPrefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val patientId = sharedPrefs.getInt("user_id", -1)

        if (patientId != -1) {
            viewModel.getAppointmentsWithDoctor(patientId)

            viewModel.appointmentsWithDoctor.observe(viewLifecycleOwner) { list ->
                adapter.updateAppointments(list)
            }
        }
    }
}