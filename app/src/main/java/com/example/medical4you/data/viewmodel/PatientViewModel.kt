package com.example.medical4you.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medical4you.data.model.Patient
import com.example.medical4you.data.repositories.PatientRepository
import kotlinx.coroutines.launch

class PatientViewModel(private val repository: PatientRepository) : ViewModel() {

    private val _allPatients = MutableLiveData<List<Patient>>()
    val allPatients: LiveData<List<Patient>> = _allPatients

    private val _selectedPatient = MutableLiveData<Patient?>()
    val selectedPatient: LiveData<Patient?> = _selectedPatient

    fun loadAllPatients() {
        viewModelScope.launch {
            _allPatients.value = repository.getAll()
        }
    }

    fun loadPatientByUserId(userId: Int) {
        viewModelScope.launch {
            _selectedPatient.value = repository.getByUserId(userId)
        }
    }

    fun insertPatient(patient: Patient) {
        viewModelScope.launch {
            repository.insert(patient)
            loadAllPatients()
        }
    }

    fun updatePatient(patient: Patient) {
        viewModelScope.launch {
            repository.update(patient)
            loadAllPatients()
        }
    }

    fun deletePatient(patient: Patient) {
        viewModelScope.launch {
            repository.delete(patient)
            loadAllPatients()
        }
    }
}