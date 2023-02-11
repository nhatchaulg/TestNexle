package com.example.test.nexle.di

import android.app.Application
import android.content.SharedPreferences
import com.example.test.nexle.MyApplication
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

    single{
        getSharedPrefs(androidApplication())
    }

    single<SharedPreferences.Editor> {
        getSharedPrefs(androidApplication()).edit()
    }
}

fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return  androidApplication.getSharedPreferences(MyApplication.PREFERENCES_FILE_KEY,  android.content.Context.MODE_PRIVATE)
}