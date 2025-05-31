package com.example.medical4you.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Doctor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = DOCTOR_NAME)
    val name: String,

    @ColumnInfo(name = SPECIALTY)
    val specialty: String,

    @ColumnInfo(name = EMAIL)
    val email: String,

    @ColumnInfo(name = PHONE_NUMBER)
    val phoneNumber: String,

    @ColumnInfo(name = LOCATION)
    val location: String,

    @ColumnInfo(name = SCHEDULE)
    val schedule: String
) {
    companion object {
        const val DOCTOR_NAME = "doctor_name"
        const val SPECIALTY = "specialty"
        const val EMAIL = "email"
        const val PHONE_NUMBER = "phone_number"
        const val LOCATION = "location"
        const val SCHEDULE = "schedule"
    }
}