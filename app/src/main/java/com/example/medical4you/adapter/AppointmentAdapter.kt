package com.example.medical4you.adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medical4you.R
import com.example.medical4you.data.model.AppointmentWithDoctor

class AppointmentAdapter(
    private var appointments: List<AppointmentWithDoctor>
) : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val doctorName: TextView = itemView.findViewById(R.id.textDoctorName)
        val dateTime: TextView = itemView.findViewById(R.id.textDateTime)
        val status: TextView = itemView.findViewById(R.id.textStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = appointments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = appointments[position]

        holder.doctorName.text = "Dr. ${item.doctor.name} (${item.doctor.specialization})"
        holder.dateTime.text = "Data: ${item.appointment.dateTime}"
        holder.status.text = "Status: ${item.appointment.status}"
    }

    fun updateAppointments(newList: List<AppointmentWithDoctor>) {
        appointments = newList
        notifyDataSetChanged()
    }
}