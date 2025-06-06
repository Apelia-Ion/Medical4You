package com.example.medical4you.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "doctors",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["user_id"])]
)
data class Doctor(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "specialization")
    val specialization: String,

    @ColumnInfo(name = "location")
    val location: String,

    @ColumnInfo(name = "schedule")
    val schedule: String,

    @ColumnInfo(name = "services")
    val services: String
) {
    companion object {
        const val TABLE_NAME = "doctors"
    }
}
