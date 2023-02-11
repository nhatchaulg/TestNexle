package com.example.test.nexle.ui.auth.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test.nexle.data.model.User
import com.example.test.nexle.databinding.ActivityAuthBinding
import com.example.test.nexle.ui.auth.viewmodel.AuthViewModel
import com.example.test.nexle.ui.main.view.MainActivity
import com.example.test.nexle.utils.UtilsSharePreference
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityAuthBinding
    val viewModel: AuthViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = _binding.root
        _binding.viewModel = viewModel
        setContentView(view)
    }

    fun getBinding(): ActivityAuthBinding {
        return _binding
    }

    fun startMainActivity(user: User?) {
        UtilsSharePreference.updateUserToken(user?.token)
        UtilsSharePreference.updateUserName(user?.displayName)
        viewModel.isShowLoading.set(false)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}