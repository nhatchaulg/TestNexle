package com.example.test.nexle

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.test.nexle.di.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {

    companion object {
        const val PREFERENCES_FILE_KEY = "com.example.test.nexle.sharepreferences"
        var mInstance: MyApplication? = null
        private lateinit var contextOfApplication: Context
    }

    fun getContext(): Context {
        return contextOfApplication
    }

    fun getSharedPreference(): SharedPreferences {
        return sharedPreferences
    }

    private val sharedPreferences: SharedPreferences by inject()
    override fun onCreate() {
        super.onCreate()
        mInstance = this
        contextOfApplication = applicationContext
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    appModule,
                    repositoryModule,
                    viewModelModule,
                    retrofitModule,
                    apiModule
                )
            )
        }
    }
}