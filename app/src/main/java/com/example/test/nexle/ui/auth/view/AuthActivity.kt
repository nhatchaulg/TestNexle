package com.example.test.nexle.ui.auth.view

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.example.test.nexle.data.model.User
import com.example.test.nexle.databinding.ActivityAuthBinding
import com.example.test.nexle.ui.main.view.MainActivity
import com.example.test.nexle.utils.UtilsSharePreference

class AuthActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)
    }

    fun getBinding(): ActivityAuthBinding {
        return _binding
    }

    fun startMainActivity(user: User?) {
        UtilsSharePreference.updateUserToken(user?.token)
        UtilsSharePreference.updateUserName(user?.displayName)
        _binding.layoutLoading.visibility = View.GONE
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}