package com.example.medical4you.viewmodel.repositories

import androidx.lifecycle.*
import com.example.medical4you.data.repositories.DoctorRepository
import com.example.medical4you.model.Doctor
import kotlinx.coroutines.launch

class DoctorViewModel(private val repository: DoctorRepository) : ViewModel() {

    val allDoctors: LiveData<List<Doctor>> = repository.allDoctors

    fun insert(doctor: Doctor) = viewModelScope.launch {
        repository.insert(doctor)
    }

    fun update(doctor: Doctor) = viewModelScope.launch {
        repository.update(doctor)
    }

    fun delete(doctor: Doctor) = viewModelScope.launch {
        repository.delete(doctor)
    }
}