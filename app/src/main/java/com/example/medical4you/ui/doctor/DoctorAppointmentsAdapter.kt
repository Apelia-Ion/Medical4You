package com.example.medical4you.ui.doctor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medical4you.R
import com.example.medical4you.data.model.AppointmentWithPatient

class DoctorAppointmentsAdapter(
    private val appointments: List<AppointmentWithPatient>
) : RecyclerView.Adapter<DoctorAppointmentsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPatientName: TextView = view.findViewById(R.id.tv_patient_name)
        val tvDateTime: TextView = view.findViewById(R.id.tv_date_time)
        val tvStatus: TextView = view.findViewById(R.id.tv_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment_doctor, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = appointments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.tvPatientName.text = "Patient: ${appointment.patient?.name ?: "Unknown"}"
        holder.tvDateTime.text = "Date & Time: ${appointment.appointment.dateTime}"
        holder.tvStatus.text = "Status: ${appointment.appointment.status}"
    }
}