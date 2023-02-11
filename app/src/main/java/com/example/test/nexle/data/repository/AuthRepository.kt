package com.example.test.nexle.data.repository

import com.example.test.nexle.data.api.ApiService
import com.example.test.nexle.data.model.LoginRequest

class AuthRepository(private val apiHelper: ApiService){
    suspend fun login(loginRequest: LoginRequest) = apiHelper.login(loginRequest)
    suspend fun register() = apiHelper.register()
}