package com.example.medical4you.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medical4you.data.AppointmentRepository
import com.example.medical4you.model.Appointment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppointmentViewModel(private val repository: AppointmentRepository) : ViewModel() {

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    fun loadAppointments() {
        viewModelScope.launch {
            _appointments.value = repository.getAllAppointments()
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