package com.example.test.nexle.ui.auth.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.test.nexle.R
import com.example.test.nexle.ui.auth.viewmodel.AuthViewModel
import com.example.test.nexle.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
    }

    private fun setupObservers() {
        viewModel.login().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {

                    }
                    Status.ERROR -> {

                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }
}