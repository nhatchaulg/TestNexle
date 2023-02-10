package com.example.test.nexle.ui.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.test.nexle.data.repository.AuthRepository
import com.example.test.nexle.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val
    authRepository: AuthRepository,
) : ViewModel() {

    fun login() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = authRepository.login()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}