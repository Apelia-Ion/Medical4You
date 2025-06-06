package com.example.medical4you.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "patients",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = [User.COLUMN_USER_ID],
            childColumns = [Patient.COLUMN_USER_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = [Patient.COLUMN_USER_ID])]
)
data class Patient(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_USER_ID)
    val userId: Int,

    @ColumnInfo(name = COLUMN_CNP)
    val cnp: String,

    @ColumnInfo(name = COLUMN_DATE_OF_BIRTH)
    val dateOfBirth: String,

    @ColumnInfo(name = COLUMN_INSURANCE_NUMBER)
    val insuranceNumber: String
) {
    companion object {
        const val TABLE_NAME = "patients"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_CNP = "cnp"
        const val COLUMN_DATE_OF_BIRTH = "date_of_birth"
        const val COLUMN_INSURANCE_NUMBER = "insurance_number"
    }
}