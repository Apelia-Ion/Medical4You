package com.example.medical4you.ui.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medical4you.R
import com.example.medical4you.data.model.User

class PendingDoctorAdapter(
    private var doctors: List<User>,
    private val onConfirmClick: (User) -> Unit,
    private val onSeeMoreClick: (User) -> Unit
) : RecyclerView.Adapter<PendingDoctorAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tv_doctor_name)
        val emailTextView: TextView = view.findViewById(R.id.tv_doctor_email)
        val btnConfirm: ImageButton = view.findViewById(R.id.btn_confirm_doctor)
        val btnSeeMore: ImageButton = view.findViewById(R.id.btn_see_more)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pending_doctor, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = doctors.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.nameTextView.text = doctor.name
        holder.emailTextView.text = doctor.email

        holder.btnConfirm.setOnClickListener { onConfirmClick(doctor) }
        holder.btnSeeMore.setOnClickListener { onSeeMoreClick(doctor) }
    }

    fun updateData(newDoctors: List<User>) {
        doctors = newDoctors
        notifyDataSetChanged()
    }
}
