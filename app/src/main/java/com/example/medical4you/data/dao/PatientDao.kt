package com.example.medical4you.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.medical4you.model.Patient
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {

    @Insert
    suspend fun insertPatient(patient: Patient)

    @Query("SELECT * FROM Patient")
    fun getAllPatients(): Flow<List<Patient>>

    @Query("DELETE FROM Patient WHERE id = :patientId")
    suspend fun deletePatientById(patientId: Long)
}