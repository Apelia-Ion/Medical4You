package com.example.medical4you.data.repositories

import androidx.lifecycle.LiveData
import com.example.medical4you.data.dao.DoctorDao
import com.example.medical4you.data.dao.UserDao
import com.example.medical4you.data.model.User


class UserRepository(private val userDao: UserDao) {

    suspend fun login(username: String, password: String): User? {
        return userDao.login(username, password)
    }

    suspend fun register(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    suspend fun searchDoctors(specialization: String?, location: String?): List<User> {
        return userDao.searchDoctors(specialization, location)
    }
}