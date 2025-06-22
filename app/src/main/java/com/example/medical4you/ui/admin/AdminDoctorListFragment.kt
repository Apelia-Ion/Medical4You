package com.example.medical4you.ui.admin

import android.os.Bundle
import android.widget.Button
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class AdminDoctorListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DoctorAdapter
    private lateinit var doctorList: List<User>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_doctor_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewDoctors)
        val closeButton = view.findViewById<Button>(R.id.btn_close)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        doctorList = listOf()

        adapter = DoctorAdapter(doctorList) { doctor ->
            deleteDoctor(doctor)
        }
        recyclerView.adapter = adapter

        loadDoctors()

        // Închide fragmentul și revine la meniul admin
        closeButton.setOnClickListener {
            // 1. Afișează meniul administrator
            activity?.findViewById<LinearLayout>(R.id.menu_container)?.visibility = View.VISIBLE
            activity?.findViewById<Button>(R.id.btn_logout)?.visibility = View.VISIBLE

            // 2. Închide fragmentul
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

        return view
    }

    private fun loadDoctors() {
        val db = MedicalAppDatabase.getDatabase(requireContext())
        val userDao = db.userDao()

        lifecycleScope.launch {
            val doctors = userDao.getUsersByType("doctor")
            doctorList = doctors
            adapter.updateData(doctorList)
        }
    }

    private fun deleteDoctor(doctor: User) {
        val db = MedicalAppDatabase.getDatabase(requireContext())
        val userDao = db.userDao()

        lifecycleScope.launch {
            userDao.deleteUser(doctor)
            Toast.makeText(requireContext(), "Doctor sters", Toast.LENGTH_SHORT).show()
            loadDoctors()
        }
    }
}
