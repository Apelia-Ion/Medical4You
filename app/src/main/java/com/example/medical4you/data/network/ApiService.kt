package com.example.medical4you.data.network

import com.example.medical4you.data.model.NewsArticle
import retrofit2.http.GET

interface ApiService {
    @GET("posts") // luăm articole demo din jsonplaceholder
    suspend fun getNews(): List<NewsArticle>
}
