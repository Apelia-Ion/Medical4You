package com.example.medical4you.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medical4you.data.PatientRepository
import com.example.medical4you.model.Patient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PatientViewModel(private val repository: PatientRepository) : ViewModel() {

    private val _patients = MutableStateFlow<List<Patient>>(emptyList())
    val patients: StateFlow<List<Patient>> = _patients

    fun loadPatients() {
        viewModelScope.launch {
            _patients.value = repository.getAllPatients()
        }
    }

    fun addPatient(patient: Patient) {
        viewModelScope.launch {
            repository.insert(patient)
            loadPatients()
        }
    }

    fun updatePatient(patient: Patient) {
        viewModelScope.launch {
            repository.update(patient)
            loadPatients()
        }
    }

    fun deletePatient(patient: Patient) {
        viewModelScope.launch {
            repository.delete(patient)
            loadPatients()
        }
    }
}