package com.example.medical4you.data.dao

import androidx.room.*
import com.example.medical4you.data.model.Appointment
import com.example.medical4you.data.model.AppointmentWithDoctor

@Dao
interface AppointmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: Appointment)

    @Update
    suspend fun updateAppointment(appointment: Appointment)

    @Delete
    suspend fun deleteAppointment(appointment: Appointment)

    @Query("SELECT * FROM appointments ORDER BY date_time ASC")
    suspend fun getAllAppointments(): List<Appointment>

    @Query("SELECT * FROM appointments WHERE patient_id = :patientId ORDER BY date_time ASC")
    suspend fun getAppointmentsForPatient(patientId: Int): List<Appointment>

    @Query("SELECT * FROM appointments WHERE doctor_id = :doctorId ORDER BY date_time ASC")
    suspend fun getAppointmentsForDoctor(doctorId: Int): List<Appointment>

    @Transaction
    @Query("SELECT * FROM appointments WHERE patient_id = :patientId ORDER BY date_time ASC")
    suspend fun getAppointmentsWithDoctor(patientId: Int): List<AppointmentWithDoctor>

}