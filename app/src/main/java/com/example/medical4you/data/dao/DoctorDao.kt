package com.example.medical4you.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.medical4you.model.Doctor

@Dao
interface DoctorDao {

    @Query("SELECT * FROM doctors ORDER BY doctor_name ASC")
    fun getAllDoctors(): LiveData<List<Doctor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctor(doctor: Doctor)

    @Update
    suspend fun updateDoctor(doctor: Doctor)

    @Delete
    suspend fun deleteDoctor(doctor: Doctor)
}