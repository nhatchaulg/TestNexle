package com.example.test.nexle.data.repository

import com.example.test.nexle.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun logout() = apiHelper.logout()
}