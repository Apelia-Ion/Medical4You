package com.example.medical4you.data.repositories

import androidx.lifecycle.LiveData
import com.example.medical4you.data.dao.DoctorDao
import com.example.medical4you.model.Doctor

class DoctorRepository(private val doctorDao: DoctorDao) {

    val allDoctors: LiveData<List<Doctor>> = doctorDao.getAllDoctors()

    suspend fun insert(doctor: Doctor) {
        doctorDao.insertDoctor(doctor)
    }

    suspend fun update(doctor: Doctor) {
        doctorDao.updateDoctor(doctor)
    }

    suspend fun delete(doctor: Doctor) {
        doctorDao.deleteDoctor(doctor)
    }
}