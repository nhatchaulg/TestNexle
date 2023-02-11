package com.example.test.nexle.data.repository

import com.example.test.nexle.data.api.ApiService

class MainRepository(private val apiHelper: ApiService) {

    suspend fun logout() = apiHelper.logout()
}