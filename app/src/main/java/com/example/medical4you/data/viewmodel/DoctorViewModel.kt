package com.example.medical4you.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medical4you.data.repositories.DoctorRepository
import com.example.medical4you.data.model.Doctor
import kotlinx.coroutines.launch

class DoctorViewModel(private val repository: DoctorRepository) : ViewModel() {

    private val _doctors = MutableLiveData<List<Doctor>>()
    val doctors: LiveData<List<Doctor>> = _doctors

    fun loadAllDoctors() {
        viewModelScope.launch {
            _doctors.value = repository.getAllDoctors()
        }
    }

    fun searchDoctorsBySpecializationOrLocation(
        specialization: String?,
        location: String?
    ) {
        viewModelScope.launch {
            _doctors.value = repository.searchDoctors(specialization, location)
        }
    }
}