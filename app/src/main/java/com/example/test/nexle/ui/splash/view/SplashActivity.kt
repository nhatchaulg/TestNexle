package com.example.test.nexle.ui.splash.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test.nexle.R
import com.example.test.nexle.ui.auth.view.AuthActivity
import com.example.test.nexle.ui.main.view.MainActivity
import com.example.test.nexle.utils.UtilsSharePreference
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        runBlocking {
            delay(300)
            if (UtilsSharePreference.getUserToken().isEmpty()) {
                startLoginAct()
            } else {
                startMainAct()
            }
        }
    }

    private fun startLoginAct() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startMainAct() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}