package com.example.medical4you.ui.pacient

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

class DoctorSearchFragment : Fragment() {

    private lateinit var doctorDao: DoctorDao
    private lateinit var specializationSpinner: Spinner
    private lateinit var locationSpinner: Spinner
    private lateinit var searchButton: Button
    private lateinit var resultLayout: LinearLayout
    private lateinit var viewModel: DoctorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_doctor_search, container, false)

        val db = MedicalAppDatabase.getDatabase(requireContext())
        doctorDao = db.doctorDao()

        specializationSpinner = view.findViewById(R.id.sp_specialization_filter)
        locationSpinner = view.findViewById(R.id.sp_location_filter)
        searchButton = view.findViewById(R.id.btn_search)
        resultLayout = view.findViewById(R.id.layout_search_results)

        specializationSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            Specialization.values()
        )

        locationSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            LocationType.values()
        )

        val repository = DoctorRepository(doctorDao)
        val factory = DoctorViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[DoctorViewModel::class.java]

        searchButton.setOnClickListener {
            val specialization = (specializationSpinner.selectedItem as Specialization).displayName
            val location = (locationSpinner.selectedItem as LocationType).displayName

            viewModel.searchDoctors(specialization, location).observe(viewLifecycleOwner) { results ->
                showResults(results)
            }
        }

        return view
    }

    private fun showResults(doctors: List<Doctor>) {
        resultLayout.removeAllViews()

        if (doctors.isEmpty()) {
            val noResult = TextView(requireContext())
            noResult.text = "No doctors found."
            resultLayout.addView(noResult)
            return
        }

        doctors.forEach { doctor ->
            val card = layoutInflater.inflate(R.layout.item_doctor_result, resultLayout, false)
            card.findViewById<TextView>(R.id.tv_doctor_name).text = doctor.name
            card.findViewById<TextView>(R.id.tv_specialization).text = "Specialization: ${doctor.specialization}"
            card.findViewById<TextView>(R.id.tv_location).text = "Location: ${doctor.location}"

            card.findViewById<Button>(R.id.btn_book).setOnClickListener {
                Toast.makeText(requireContext(), "Booking with ${doctor.name}", Toast.LENGTH_SHORT).show()
                // TODO: Navigate to booking screen
            }

            card.findViewById<Button>(R.id.btn_reviews).setOnClickListener {
                Toast.makeText(requireContext(), "Opening reviews for ${doctor.name}", Toast.LENGTH_SHORT).show()
                // TODO: Navigate to reviews
            }

            resultLayout.addView(card)
        }
    }
}