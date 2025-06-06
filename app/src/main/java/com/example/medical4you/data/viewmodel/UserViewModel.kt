package com.example.medical4you.viewmodel.repositories

import androidx.lifecycle.*
import com.example.medical4you.data.model.User
import com.example.medical4you.data.repositories.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = repository.login(username, password)
            _currentUser.value = user
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            repository.register(user)
        }
    }

    fun searchDoctors(specialty: String?, location: String?): LiveData<List<User>> {
        val result = MutableLiveData<List<User>>()
        viewModelScope.launch {
            result.value = repository.searchDoctors(specialty, location)
        }
        return result
    }
}