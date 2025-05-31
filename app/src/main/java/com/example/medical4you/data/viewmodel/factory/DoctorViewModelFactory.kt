package com.example.medical4you.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.medical4you.data.repositories.DoctorRepository
import com.example.medical4you.viewmodel.DoctorViewModel

class DoctorViewModelFactory(private val repository: DoctorRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DoctorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DoctorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}