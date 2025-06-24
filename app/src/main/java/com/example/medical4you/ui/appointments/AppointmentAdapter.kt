package com.example.medical4you.ui.appointments


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medical4you.R
import com.example.medical4you.data.model.AppointmentWithDoctor

class AppointmentAdapter(
    private var appointments: List<AppointmentWithDoctor>
) : RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    inner class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTimeText: TextView = itemView.findViewById(R.id.textDateTime)
        val statusText: TextView = itemView.findViewById(R.id.textStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val item = appointments[position]
        holder.dateTimeText.text = "Data: ${item.appointment.dateTime} - cu Dr. ${item.doctor.name}"
        holder.statusText.text = "Status: ${item.appointment.status}"
    }

    override fun getItemCount(): Int = appointments.size

    fun updateAppointments(newList: List<AppointmentWithDoctor>) {
        appointments = newList
        notifyDataSetChanged()
    }
}