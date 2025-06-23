package com.example.medical4you.ui.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medical4you.R
import com.example.medical4you.data.model.User

class DoctorAdapter(
    private var doctors: List<User>,
    private val onDeleteClick: (User) -> Unit,
    private val onSeeMoreClick: (User) -> Unit
) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_doctor_name)
        val email: TextView = itemView.findViewById(R.id.tv_doctor_email)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btn_delete_doctor)
        val btnSeeMore: ImageButton = itemView.findViewById(R.id.btn_see_more)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.name.text = doctor.username
        holder.email.text = doctor.email

        holder.btnDelete.setOnClickListener {
            onDeleteClick(doctor)
        }

        holder.btnSeeMore.setOnClickListener {
            onSeeMoreClick(doctor)
        }
    }

    override fun getItemCount(): Int = doctors.size

    fun updateData(newDoctors: List<User>) {
        doctors = newDoctors
        notifyDataSetChanged()
    }
}
