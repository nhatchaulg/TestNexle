package com.example.test.nexle.ui.splash.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.test.nexle.R
import com.example.test.nexle.ui.auth.view.AuthActivity
import com.example.test.nexle.ui.main.view.MainActivity
import com.example.test.nexle.utils.UtilsSharePreference

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            if (UtilsSharePreference.getUserToken().isNullOrEmpty()) {
                startLoginAct()
            } else {
                startMainAct()
            }
        }, 300)
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