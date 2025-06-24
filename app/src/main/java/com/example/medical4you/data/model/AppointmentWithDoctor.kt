package com.example.medical4you.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class AppointmentWithDoctor(
    @Embedded val appointment: Appointment,

    @Relation(
        parentColumn = "doctor_id",
        entityColumn = "user_id"
    )
    val doctor: Doctor
)