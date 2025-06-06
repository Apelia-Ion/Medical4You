package com.example.medical4you.data.repositories

import com.example.medical4you.data.dao.AppointmentDao
import com.example.medical4you.data.model.Appointment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppointmentRepository(private val dao: AppointmentDao) {

    suspend fun getAllAppointments(): List<Appointment> = withContext(Dispatchers.IO) {
        dao.getAllAppointments()
    }

    suspend fun getAppointmentsForDoctor(doctorId: Int): List<Appointment> = withContext(Dispatchers.IO) {
        dao.getAppointmentsForDoctor(doctorId)
    }

    suspend fun getAppointmentsForPatient(patientId: Int): List<Appointment> = withContext(Dispatchers.IO) {
        dao.getAppointmentsForPatient(patientId)
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
