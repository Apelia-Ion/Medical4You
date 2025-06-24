package com.example.medical4you.ui.pacient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.medical4you.R
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.data.model.Appointment
import com.example.medical4you.data.repositories.AppointmentRepository
import com.example.medical4you.data.viewmodel.AppointmentViewModel
import com.example.medical4you.viewmodel.factory.AppointmentViewModelFactory
import kotlinx.coroutines.launch

class MakeAppointmentFragment : Fragment() {

    private lateinit var appointmentViewModel: AppointmentViewModel
    private var doctorId: Int = -1
    private var patientId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doctorId = arguments?.getInt("doctor_id") ?: -1

        val prefs = requireActivity().getSharedPreferences("user_prefs", 0)
        patientId = prefs.getInt("user_id", -1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_make_appointment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dateEdit = view.findViewById<EditText>(R.id.edit_date)
        val timeEdit = view.findViewById<EditText>(R.id.edit_time)
        val submitButton = view.findViewById<Button>(R.id.btn_submit_appointment)
        val doctorNameText = view.findViewById<TextView>(R.id.tv_doctor_name)

        val db = MedicalAppDatabase.getDatabase(requireContext())
        val dao = db.appointmentDao()
        val doctorDao = db.doctorDao()
        val repo = AppointmentRepository(dao)
        val factory = AppointmentViewModelFactory(repo)
        appointmentViewModel = ViewModelProvider(this, factory)[AppointmentViewModel::class.java]

        lifecycleScope.launch {
            val doctor = doctorDao.getDoctorById(doctorId)
            doctorNameText.text = "Programare la Dr. ${doctor.name}"
        }

        submitButton.setOnClickListener {
            val date = dateEdit.text.toString()
            val time = timeEdit.text.toString()

            if (date.isBlank() || time.isBlank()) {
                Toast.makeText(requireContext(), "Completează data și ora", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val appointment = Appointment(
                patientId = patientId,
                doctorId = doctorId,
                dateTime = "$date $time",
                status = "Programat"
            )

            lifecycleScope.launch {
                appointmentViewModel.insertAppointment(appointment)
                Toast.makeText(requireContext(), "Programare salvată", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }
}