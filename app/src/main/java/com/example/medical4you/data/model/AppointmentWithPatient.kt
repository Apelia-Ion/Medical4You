package com.example.medical4you.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class AppointmentWithPatient(
    @Embedded val appointment: Appointment,

    @Relation(
        parentColumn = "patient_id",
        entityColumn = "user_id"
    )
    val patient: User?
)