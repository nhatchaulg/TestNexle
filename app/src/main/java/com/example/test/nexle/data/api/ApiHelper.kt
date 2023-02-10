package com.example.test.nexle.data.api

class ApiHelper(private val apiService: ApiService){
    suspend fun login() = apiService.login()
    suspend fun register() = apiService.resister()
    suspend fun logout() = apiService.logout()
}