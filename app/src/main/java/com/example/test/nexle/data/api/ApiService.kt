package com.example.test.nexle.data.api

import com.example.test.nexle.data.model.LoginRequest
import com.example.test.nexle.data.model.RegisterRequest
import com.example.test.nexle.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/signin")
    suspend fun login(@Body requestBody: LoginRequest): User

    @POST("auth/signup")
    suspend fun register(@Body requestBody: RegisterRequest): User

    @POST("auth/logout")
    suspend fun logout(): Response<Unit>
}