package com.example.medical4you.ui.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medical4you.R
import com.example.medical4you.data.model.User

class PatientAdapter(
    private var patients: List<User>,
    private val onDeleteClick: (User) -> Unit
) : RecyclerView.Adapter<PatientAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tv_patient_name)
        val emailTextView: TextView = view.findViewById(R.id.tv_patient_email)
        val btnDelete: ImageButton = view.findViewById(R.id.btn_delete_patient)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_patient, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = patients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val patient = patients[position]
        holder.nameTextView.text = patient.name
        holder.emailTextView.text = patient.email

        holder.btnDelete.setOnClickListener { onDeleteClick(patient) }
    }

    fun updateData(newList: List<User>) {
        patients = newList
        notifyDataSetChanged()
    }
}
