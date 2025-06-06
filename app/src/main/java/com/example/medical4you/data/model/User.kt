package com.example.medical4you.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_USER_ID)
    val userId: Int = 0,

    @ColumnInfo(name = COLUMN_USERNAME)
    val username: String,

    @ColumnInfo(name = COLUMN_PASSWORD)
    val password: String,

    @ColumnInfo(name = COLUMN_USER_TYPE)
    val userType: String,

    @ColumnInfo(name = COLUMN_NAME)
    val name: String? = null,

    @ColumnInfo(name = COLUMN_SPECIALIZATION)
    val specialization: String? = null,

    @ColumnInfo(name = COLUMN_LOCATION)
    val location: String? = null,

    @ColumnInfo(name = COLUMN_SCHEDULE)
    val schedule: String? = null,

    @ColumnInfo(name = COLUMN_SERVICES)
    val services: String? = null


) {
    companion object {
        const val TABLE_NAME = "users"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_USER_TYPE = "user_type"
        const val COLUMN_NAME = "name"
        const val COLUMN_SPECIALIZATION = "specialization"
        const val COLUMN_LOCATION = "location"
        const val COLUMN_SCHEDULE = "schedule"
        const val COLUMN_SERVICES = "services"
    }

}