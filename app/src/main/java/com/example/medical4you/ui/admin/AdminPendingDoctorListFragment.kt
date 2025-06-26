package com.example.medical4you.ui.admin

import android.content.Intent
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
import com.example.medical4you.ui.doctor.DoctorProfileActivity
import kotlinx.coroutines.launch

class AdminPendingDoctorListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PendingDoctorAdapter
    private lateinit var pendingList: List<User>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_pending_doctor_list, container, false)
        val closeButton = view.findViewById<Button>(R.id.btn_close)
        recyclerView = view.findViewById(R.id.recyclerViewPendingDoctors)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        pendingList = listOf()

        adapter = PendingDoctorAdapter(
            pendingList,
            onConfirmClick = { doctor -> confirmDoctor(doctor) },
            onSeeMoreClick = { doctor ->
                Toast.makeText(requireContext(), "See more: ${doctor.username}", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), DoctorProfileActivity::class.java)
                intent.putExtra("doctor_id", doctor.userId)
                startActivity(intent)
            }
        )

        recyclerView.adapter = adapter
        loadPendingDoctors()

        closeButton.setOnClickListener {
            activity?.findViewById<LinearLayout>(R.id.menu_container)?.visibility = View.VISIBLE
            activity?.findViewById<Button>(R.id.btn_logout)?.visibility = View.VISIBLE
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

        return view
    }

    private fun loadPendingDoctors() {
        val db = MedicalAppDatabase.getDatabase(requireContext())
        val userDao = db.userDao()

        lifecycleScope.launch {
            val doctors = userDao.getPendingDoctors()
            pendingList = doctors
            adapter.updateData(pendingList)
        }
    }

    private fun confirmDoctor(doctor: User) {
        val db = MedicalAppDatabase.getDatabase(requireContext())
        val userDao = db.userDao()

        lifecycleScope.launch {
            val updatedDoctor = doctor.copy(confirmed = true)
            userDao.updateUser(updatedDoctor)
            Toast.makeText(requireContext(), "Doctor confirmed", Toast.LENGTH_SHORT).show()
            loadPendingDoctors()
        }
    }
}
