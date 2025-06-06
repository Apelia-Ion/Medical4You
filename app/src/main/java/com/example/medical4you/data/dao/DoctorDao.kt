package com.example.medical4you.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.medical4you.data.model.Doctor


@Dao
interface DoctorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctor(doctor: Doctor)

    @Update
    suspend fun updateDoctor(doctor: Doctor)

    @Delete
    suspend fun deleteDoctor(doctor: Doctor)

    @Query("SELECT * FROM doctors")
    suspend fun getAllDoctors(): List<Doctor>

    @Query("""
        SELECT * FROM doctors 
        WHERE (:specialization IS NULL OR specialization LIKE '%' || :specialization || '%')
          AND (:location IS NULL OR location LIKE '%' || :location || '%')
    """)
    suspend fun searchDoctors(specialization: String?, location: String?): List<Doctor>

    @Query("SELECT * FROM doctors WHERE user_id = :userId")
    suspend fun getDoctorByUserId(userId: Int): Doctor?
}