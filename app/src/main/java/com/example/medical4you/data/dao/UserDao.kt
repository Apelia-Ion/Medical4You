package com.example.medical4you.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.medical4you.data.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Insert
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): User?

    @Query("SELECT * FROM users WHERE user_id = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("""
    SELECT * FROM users
    WHERE user_type = 'doctor'
      AND (:specialization IS NULL OR specialization LIKE '%' || :specialization || '%')
      AND (:location IS NULL OR location LIKE '%' || :location || '%')
""")
    suspend fun searchDoctors(specialization: String?, location: String?): List<User>

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE user_type = :userType")
    suspend fun getUsersByType(userType: String): List<User>

    @androidx.room.Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE user_type = 'doctor' AND confirmed = 1")
    suspend fun getConfirmedDoctors(): List<User>

    @Query("SELECT * FROM users WHERE user_type = 'doctor' AND confirmed = 0")
    suspend fun getPendingDoctors(): List<User>

    @Update
    suspend fun updateUser(user: User)



}
