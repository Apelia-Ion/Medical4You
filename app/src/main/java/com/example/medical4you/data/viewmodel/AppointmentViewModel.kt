package com.example.medical4you.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medical4you.data.model.Appointment
import com.example.medical4you.data.repositories.AppointmentRepository
import kotlinx.coroutines.launch

class AppointmentViewModel(private val repository: AppointmentRepository) : ViewModel() {

    private val _appointments = MutableLiveData<List<Appointment>>()
    val appointments: LiveData<List<Appointment>> = _appointments

    fun loadAppointments() {
        viewModelScope.launch {
            _appointments.value = repository.getAllAppointments()
        }
    }

    fun loadAppointmentsForDoctor(doctorId: Int) {
        viewModelScope.launch {
            val result = repository.getAppointmentsForDoctor(doctorId)
            _appointments.value = result
        }
    }

    fun loadAppointmentsForPatient(patientId: Int) {
        viewModelScope.launch {
            val result = repository.getAppointmentsForPatient(patientId)
            _appointments.value = result
        }
    }

    fun addAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.insert(appointment)
            loadAppointments()
        }
    }

    fun updateAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.update(appointment)
            loadAppointments()
        }
    }

    fun deleteAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.delete(appointment)
            loadAppointments()
        }
    }
}