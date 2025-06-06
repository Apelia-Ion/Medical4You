package com.example.medical4you.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.medical4you.data.dao.AppointmentDao
import com.example.medical4you.data.dao.DoctorDao
import com.example.medical4you.data.dao.PatientDao
import com.example.medical4you.data.dao.ReviewDao
import com.example.medical4you.data.dao.UserDao
import com.example.medical4you.data.model.Appointment
import com.example.medical4you.data.model.Doctor
import com.example.medical4you.data.model.Patient
import com.example.medical4you.data.model.User
import com.example.medical4you.data.model.Review


@Database(
    entities = [User::class, Doctor::class, Patient::class, Appointment::class, Review::class],
    version = 1,
    exportSchema = false
)
abstract class MedicalAppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun doctorDao(): DoctorDao
    abstract fun patientDao(): PatientDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun reviewDao(): ReviewDao

    companion object {
        @Volatile
        private var INSTANCE: MedicalAppDatabase? = null

        fun getDatabase(context: Context): MedicalAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MedicalAppDatabase::class.java,
                    "medical_app_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}