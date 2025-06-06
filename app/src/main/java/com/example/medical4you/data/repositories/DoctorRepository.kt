package com.example.medical4you.data.repositories

import com.example.medical4you.data.dao.DoctorDao
import com.example.medical4you.data.model.Doctor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DoctorRepository(private val doctorDao: DoctorDao) {

    suspend fun getAllDoctors(): List<Doctor> = withContext(Dispatchers.IO) {
        doctorDao.getAllDoctors()
    }

    suspend fun searchDoctors(specialization: String?, location: String?): List<Doctor> =
        withContext(Dispatchers.IO) {
            doctorDao.searchDoctors(specialization, location)
        }

    suspend fun insertDoctor(doctor: Doctor) = withContext(Dispatchers.IO) {
        doctorDao.insertDoctor(doctor)
    }

    suspend fun getDoctorByUserId(userId: Int): Doctor? = withContext(Dispatchers.IO) {
        doctorDao.getDoctorByUserId(userId)
    }
}