package com.example.test.nexle.utils

import android.content.Context
import com.example.test.nexle.MyApplication

class UtilsSharePreference() {

    companion object {
        private const val USER_TOKEN = "com.example.test.nexle.token"
        private const val USER_NAME = "com.example.test.nexle.token"

        fun getUserToken(): String {
            return MyApplication.mInstance?.getSharedPreferences(
                MyApplication.PREFERENCES_FILE_KEY,
                Context.MODE_PRIVATE
            )?.getString(USER_TOKEN, "") ?: ""
        }

        fun updateUserToken(token: String? = null) {
            MyApplication.mInstance?.getSharedPreferences(
                MyApplication.PREFERENCES_FILE_KEY,
                Context.MODE_PRIVATE
            )?.edit()?.putString(USER_TOKEN, token)?.apply()
        }

        fun getUserName(): String {
            return MyApplication.mInstance?.getSharedPreferences(
                MyApplication.PREFERENCES_FILE_KEY,
                Context.MODE_PRIVATE
            )?.getString(USER_NAME, "") ?: ""
        }

        fun updateUserName(userName: String?) {
            MyApplication.mInstance?.getSharedPreferences(
                MyApplication.PREFERENCES_FILE_KEY,
                Context.MODE_PRIVATE
            )?.edit()?.putString(USER_NAME, userName)?.apply()
        }
    }


}

