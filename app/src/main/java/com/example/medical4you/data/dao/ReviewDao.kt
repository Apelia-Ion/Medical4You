package com.example.medical4you.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.medical4you.data.model.Review
@Dao
interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReview(review: Review)

    @Query("SELECT * FROM reviews WHERE doctor_id = :doctorId")
    suspend fun getReviewsForDoctor(doctorId: Int): List<Review>
}