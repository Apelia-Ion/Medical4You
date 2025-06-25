package com.example.medical4you.ui.pacient

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.medical4you.R
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.data.dao.DoctorDao
import com.example.medical4you.enums.Specialization
import com.example.medical4you.enums.LocationType
import com.example.medical4you.data.model.Doctor
import com.example.medical4you.data.repositories.DoctorRepository
import com.example.medical4you.data.viewmodel.DoctorViewModel
import com.example.medical4you.viewmodel.factory.DoctorViewModelFactory
import com.example.medical4you.ui.appointments.ScheduleAppointmentActivity

class DoctorSearchFragment : Fragment() {

    private lateinit var spSpecialization: Spinner
    private lateinit var spLocation: Spinner
    private lateinit var btnSearch: Button
    private lateinit var resultLayout: LinearLayout
    private lateinit var viewModel: DoctorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_doctor_search, container, false)

        spSpecialization = view.findViewById(R.id.sp_specialization_filter)
        spLocation = view.findViewById(R.id.sp_location_filter)
        btnSearch = view.findViewById(R.id.btn_search)
        resultLayout = view.findViewById(R.id.layout_search_results)

        spSpecialization.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            Specialization.values().map { it.displayName }
        )

        spLocation.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            LocationType.values().map { it.displayName }
        )

        val db = MedicalAppDatabase.getDatabase(requireContext())
        val factory = DoctorViewModelFactory(DoctorRepository(db.doctorDao()))
        viewModel = ViewModelProvider(this, factory)[DoctorViewModel::class.java]

        btnSearch.setOnClickListener {
            val specialization = spSpecialization.selectedItem.toString()
            val location = spLocation.selectedItem.toString()

            viewModel.searchDoctors(specialization, location).observe(viewLifecycleOwner) { results ->
                displayResults(results)
            }
        }

        return view
    }

    private fun displayResults(doctors: List<Doctor>) {
        resultLayout.removeAllViews()

        if (doctors.isEmpty()) {
            val noResultText = TextView(requireContext()).apply {
                text = "No doctors found."
                textSize = 16f
                setPadding(8, 16, 8, 16)
            }
            resultLayout.addView(noResultText)
            return
        }

        doctors.forEach { doctor ->
            val cardView = layoutInflater.inflate(R.layout.item_doctor_result, resultLayout, false)

            cardView.findViewById<TextView>(R.id.tv_doctor_name).text = doctor.name
            cardView.findViewById<TextView>(R.id.tv_specialization).text = "Specialization: ${doctor.specialization}"
            cardView.findViewById<TextView>(R.id.tv_location).text = "Location: ${doctor.location}"

            cardView.findViewById<Button>(R.id.btn_book).setOnClickListener {
                val intent = Intent(requireContext(), ScheduleAppointmentActivity::class.java).apply {
                    putExtra("doctor_id", doctor.userId)
                    putExtra("doctor_name", doctor.name)
                }
                startActivity(intent)
            }

            cardView.findViewById<Button>(R.id.btn_reviews).setOnClickListener {
                Toast.makeText(requireContext(), "Reviews coming soon...", Toast.LENGTH_SHORT).show()
                // TODO: Replace with review activity when implemented
            }

            resultLayout.addView(cardView)
        }
    }
}