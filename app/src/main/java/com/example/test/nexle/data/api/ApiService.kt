package com.example.test.nexle.data.api

import com.example.test.nexle.data.model.LoginRequest
import com.example.test.nexle.data.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/signin")
    suspend fun login(@Body requestBody: LoginRequest): User

    @POST("auth/signun")
    suspend fun register(): User

    @POST("auth/logout")
    suspend fun logout(): User
}