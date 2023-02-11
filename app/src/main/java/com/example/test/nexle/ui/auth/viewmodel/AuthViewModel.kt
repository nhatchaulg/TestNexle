package com.example.test.nexle.ui.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.test.nexle.data.model.LoginRequest
import com.example.test.nexle.data.model.RegisterRequest
import com.example.test.nexle.data.repository.AuthRepository
import com.example.test.nexle.utils.Resource
import kotlinx.coroutines.Dispatchers

class AuthViewModel constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    fun login(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = authRepository.login(LoginRequest(email, password))))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun register(firstName: String,lastName: String, email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = authRepository.register(RegisterRequest(firstName, lastName, email, password))))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}