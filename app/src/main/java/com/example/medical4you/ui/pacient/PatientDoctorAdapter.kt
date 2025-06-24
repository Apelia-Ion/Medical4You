package com.example.medical4you.ui.pacient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medical4you.R
import com.example.medical4you.data.model.Doctor

class PatientDoctorAdapter(
    private var doctors: List<Doctor>,
    private val onScheduleClick: (Doctor) -> Unit
) : RecyclerView.Adapter<PatientDoctorAdapter.DoctorViewHolder>() {

    inner class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_doctor_name)
        val specialization: TextView = itemView.findViewById(R.id.tv_specialization)
        val location: TextView = itemView.findViewById(R.id.tv_location)
        val btnSchedule: Button = itemView.findViewById(R.id.btn_schedule)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_patient_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.name.text = doctor.name
        holder.specialization.text = "Specializare: ${doctor.specialization}"
        holder.location.text = "Locație: ${doctor.location}"

        holder.btnSchedule.setOnClickListener {
            onScheduleClick(doctor)
        }
    }

    override fun getItemCount(): Int = doctors.size

    fun updateDoctors(newDoctors: List<Doctor>) {
        doctors = newDoctors
        notifyDataSetChanged()
    }
}