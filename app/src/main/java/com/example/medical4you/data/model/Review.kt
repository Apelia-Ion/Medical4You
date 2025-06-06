package com.example.medical4you.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "reviews",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["patient_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["doctor_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["patient_id"]),
        Index(value = ["doctor_id"])
    ]
)
data class Review(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_REVIEW_ID)
    val reviewId: Int = 0,

    @ColumnInfo(name = COLUMN_PATIENT_ID)
    val patientId: Int,

    @ColumnInfo(name = COLUMN_DOCTOR_ID)
    val doctorId: Int,

    @ColumnInfo(name = COLUMN_RATING)
    val rating: Float,

    @ColumnInfo(name = COLUMN_COMMENT)
    val comment: String
) {
    companion object {
        const val TABLE_NAME = "reviews"
        const val COLUMN_REVIEW_ID = "review_id"
        const val COLUMN_PATIENT_ID = "patient_id"
        const val COLUMN_DOCTOR_ID = "doctor_id"
        const val COLUMN_RATING = "rating"
        const val COLUMN_COMMENT = "comment"
    }
}