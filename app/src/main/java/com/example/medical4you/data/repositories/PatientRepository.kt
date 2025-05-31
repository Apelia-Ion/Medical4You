package com.example.medical4you.data.repositories

import com.example.medical4you.data.local.PatientDao
import com.example.medical4you.model.Patient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PatientRepository(private val dao: PatientDao) {

    suspend fun getAllPatients(): List<Patient> = withContext(Dispatchers.IO) {
        dao.getAll()
    }

    suspend fun insert(patient: Patient) = withContext(Dispatchers.IO) {
        dao.insert(patient)
    }

    suspend fun update(patient: Patient) = withContext(Dispatchers.IO) {
        dao.update(patient)
    }

    suspend fun delete(patient: Patient) = withContext(Dispatchers.IO) {
        dao.delete(patient)
    }
}