package com.example.medical4you.data.repositories

import com.example.medical4you.data.dao.PatientDao
import com.example.medical4you.data.model.Patient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PatientRepository(private val dao: PatientDao) {

    suspend fun insert(patient: Patient) = withContext(Dispatchers.IO) {
        dao.insert(patient)
    }

    suspend fun update(patient: Patient) = withContext(Dispatchers.IO) {
        dao.update(patient)
    }

    suspend fun delete(patient: Patient) = withContext(Dispatchers.IO) {
        dao.delete(patient)
    }

    suspend fun getAll(): List<Patient> = withContext(Dispatchers.IO) {
        dao.getAll()
    }

    suspend fun getByUserId(id: Int): Patient? = withContext(Dispatchers.IO) {
        dao.getById(id)
    }
}