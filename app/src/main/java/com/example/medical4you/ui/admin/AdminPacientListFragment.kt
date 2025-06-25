package com.example.medical4you.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medical4you.R
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.data.model.User
import kotlinx.coroutines.launch

class AdminPatientListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PatientAdapter
    private lateinit var patientList: List<User>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_pacient_list, container, false)
        val closeButton = view.findViewById<Button>(R.id.btn_close)
        recyclerView = view.findViewById(R.id.recyclerViewPatients)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        patientList = listOf()

        adapter = PatientAdapter(
            patientList,
            onDeleteClick = { patient -> deletePatient(patient) }
        )

        recyclerView.adapter = adapter
        loadPatients()

        closeButton.setOnClickListener {
            activity?.findViewById<LinearLayout>(R.id.menu_container)?.visibility = View.VISIBLE
            activity?.findViewById<Button>(R.id.btn_logout)?.visibility = View.VISIBLE
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

        return view
    }

    private fun loadPatients() {
        val db = MedicalAppDatabase.getDatabase(requireContext())
        val userDao = db.userDao()

        lifecycleScope.launch {
            val patients = userDao.getUsersByType("pacient")
            patientList = patients
            adapter.updateData(patientList)
        }
    }

    private fun deletePatient(patient: User) {
        val db = MedicalAppDatabase.getDatabase(requireContext())
        val userDao = db.userDao()
        val patientDao = db.patientDao()

        lifecycleScope.launch {
            userDao.deleteUser(patient)
            patientDao.deleteByUserId(patient.userId) // legătură pe user_id în tabela patients
            Toast.makeText(requireContext(), "Pacient șters", Toast.LENGTH_SHORT).show()
            loadPatients()
        }
    }
}
