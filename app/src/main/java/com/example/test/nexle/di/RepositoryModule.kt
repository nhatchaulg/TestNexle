package com.example.test.nexle.di

import com.example.test.nexle.data.repository.AuthRepository
import com.example.test.nexle.data.repository.MainRepository
import org.koin.dsl.module


val repositoryModule = module {
    single { AuthRepository(get()) }
    single { MainRepository(get()) }
}
