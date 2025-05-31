package com.example.medical4you

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Appointment::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appointmentDao(): AppointmentDao
    abstract fun patientDao(): PatientDao
    abstract fun doctorDao(): DoctorDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appointments_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}