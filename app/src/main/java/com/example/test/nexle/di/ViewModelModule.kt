package com.example.test.nexle.di

import com.example.test.nexle.ui.auth.viewmodel.AuthViewModel
import com.example.test.nexle.ui.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        AuthViewModel(get())
    }
    viewModel {
        MainViewModel(get())
    }
}