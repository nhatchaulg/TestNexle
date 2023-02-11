package com.example.test.nexle.ui.base

import android.app.Application
import com.example.test.nexle.di.apiModule
import com.example.test.nexle.di.repositoryModule
import com.example.test.nexle.di.retrofitModule
import com.example.test.nexle.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule,
                    retrofitModule,
                    apiModule
                )
            )
        }
    }
}