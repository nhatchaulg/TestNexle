package com.example.test.nexle.data.repository

import com.example.test.nexle.data.api.ApiHelper

class AuthRepository(private val apiHelper: ApiHelper){
    suspend fun login() = apiHelper.login()
    suspend fun register() = apiHelper.register()
}