package com.example.test.nexle.ui.main.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.test.nexle.data.repository.MainRepository
import com.example.test.nexle.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel constructor(
    private val mainRepository: MainRepository,
) : ViewModel() {

    val displayName by lazy { ObservableField("") }
    val isShowLoading by lazy { ObservableField(false) }

    fun logout() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.logout()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}