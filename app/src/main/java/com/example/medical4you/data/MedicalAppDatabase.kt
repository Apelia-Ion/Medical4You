package com.example.medical4you.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.medical4you.data.dao.*
import com.example.medical4you.data.model.*
import androidx.room.migration.Migration



@Database(
    entities = [User::class, Doctor::class, Patient::class, Appointment::class, Review::class],
    version = 2,
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

        // ✅ MIGRAREA de la versiunea 1 → 2 (adăugăm coloana "email")
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE users ADD COLUMN email TEXT")
            }
        }

        fun getDatabase(context: Context): MedicalAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MedicalAppDatabase::class.java,
                    "medical_app_db"
                )
                    .addMigrations(MIGRATION_1_2) // ✅ adaugă migrarea
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
