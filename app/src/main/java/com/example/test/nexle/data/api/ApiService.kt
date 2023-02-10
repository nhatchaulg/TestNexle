package com.example.test.nexle.data.api

import com.example.test.nexle.data.model.User
import retrofit2.http.POST

interface ApiService {
    @POST("auth/signin")
    suspend fun login(): User

    @POST("auth/signun")
    suspend fun resister(): User

    @POST("auth/logout")
    suspend fun logout(): User
}