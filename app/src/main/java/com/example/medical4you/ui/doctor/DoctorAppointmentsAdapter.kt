package com.example.medical4you.ui.doctor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.medical4you.R
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.data.model.AppointmentWithPatient
import kotlinx.coroutines.launch

    class DoctorAppointmentsAdapter(
private val activity: AppCompatActivity,  // Transmitem activity
private val appointments: List<AppointmentWithPatient>
) : RecyclerView.Adapter<DoctorAppointmentsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPatientName: TextView = view.findViewById(R.id.tv_patient_name)
        val tvDateTime: TextView = view.findViewById(R.id.tv_date_time)
        val tvStatus: TextView = view.findViewById(R.id.tv_status)
        val btnAccept: View = view.findViewById(R.id.btn_accept)
        val btnReject: View = view.findViewById(R.id.btn_reject)
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
        holder.tvDateTime.text = "Date and Time: ${appointment.appointment.dateTime}" // "&" evitat
        holder.tvStatus.text = "Status: ${appointment.appointment.status}"

        holder.btnAccept.setOnClickListener {
            updateAppointmentStatus(holder, appointment.appointment.appointmentId, "Accepted")
        }

        holder.btnReject.setOnClickListener {
            updateAppointmentStatus(holder, appointment.appointment.appointmentId, "Rejected")
        }
    }

    private fun updateAppointmentStatus(holder: ViewHolder, appointmentId: Int, newStatus: String) {
        val db = MedicalAppDatabase.getDatabase(activity)

        activity.lifecycleScope.launch {
            db.appointmentDao().updateStatus(appointmentId, newStatus)
            Toast.makeText(activity, "Appointment $newStatus", Toast.LENGTH_SHORT).show()
            holder.tvStatus.text = "Status: $newStatus"
        }
    }
}