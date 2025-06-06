package com.example.medical4you.data.model

import androidx.room.*
import com.example.medical4you.data.model.Appointment.Companion.COLUMN_DOCTOR_ID
import com.example.medical4you.data.model.Appointment.Companion.COLUMN_PATIENT_ID

@Entity(
    tableName = Appointment.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = [User.COLUMN_USER_ID],
            childColumns = [Appointment.COLUMN_PATIENT_ID],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = [User.COLUMN_USER_ID],
            childColumns = [Appointment.COLUMN_DOCTOR_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = [Appointment.COLUMN_PATIENT_ID]),
        Index(value = [Appointment.COLUMN_DOCTOR_ID])
    ]
)
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_APPOINTMENT_ID)
    val appointmentId: Int = 0,

    @ColumnInfo(name = COLUMN_PATIENT_ID)
    val patientId: Int,

    @ColumnInfo(name = COLUMN_DOCTOR_ID)
    val doctorId: Int,

    @ColumnInfo(name = COLUMN_DATE_TIME)
    val dateTime: String,

    @ColumnInfo(name = COLUMN_STATUS)
    val status: String
) {
    companion object {
        const val TABLE_NAME = "appointments"
        const val COLUMN_APPOINTMENT_ID = "appointment_id"
        const val COLUMN_PATIENT_ID = "patient_id"
        const val COLUMN_DOCTOR_ID = "doctor_id"
        const val COLUMN_DATE_TIME = "date_time"
        const val COLUMN_STATUS = "status"
    }
}
