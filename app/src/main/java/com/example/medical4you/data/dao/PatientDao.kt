package com.example.medical4you.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.medical4you.data.model.Patient

@Dao
interface PatientDao {

    //comment here
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: Patient)

    @Update
    suspend fun update(patient: Patient)

    @Delete
    suspend fun delete(patient: Patient)

    @Query("DELETE FROM patients WHERE user_id = :userId")
    suspend fun deleteByUserId(userId: Int)

    @Query("SELECT * FROM patients")
    suspend fun getAll(): List<Patient>

    @Query("SELECT * FROM patients WHERE user_id = :id")
    suspend fun getById(id: Int): Patient?

    @Query("SELECT * FROM patients WHERE user_id = :userId LIMIT 1")
    fun getPatientByUserId(userId: Int): Patient?
}