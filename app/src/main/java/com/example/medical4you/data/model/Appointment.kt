package com.example.medical4you

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = PATIENT_NAME)
    val patientName: String,

    @ColumnInfo(name = DOCTOR_NAME)
    val doctorName: String,

    @ColumnInfo(name = DATE)
    val date: String,

    @ColumnInfo(name = TIME)
    val time: String
) {
    companion object {
        const val PATIENT_NAME = "patient_name"
        const val DOCTOR_NAME = "doctor_name"
        const val DATE = "appointment_date"
        const val TIME = "appointment_time"
    }
}