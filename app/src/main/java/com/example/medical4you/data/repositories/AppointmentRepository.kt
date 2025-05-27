package com.example.medical4you.data.repositories

import com.example.medical4you.model.Appointment
import com.example.medical4you.data.local.AppointmentDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppointmentRepository(private val dao: AppointmentDao) {

    suspend fun getAllAppointments(): List<Appointment> = withContext(Dispatchers.IO) {
        dao.getAllAppointments()
    }

    suspend fun insert(appointment: Appointment) = withContext(Dispatchers.IO) {
        dao.insertAppointment(appointment)
    }

    suspend fun update(appointment: Appointment) = withContext(Dispatchers.IO) {
        dao.updateAppointment(appointment)
    }

    suspend fun delete(appointment: Appointment) = withContext(Dispatchers.IO) {
        dao.deleteAppointment(appointment)
    }
}