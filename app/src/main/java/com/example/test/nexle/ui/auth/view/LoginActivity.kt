package com.example.test.nexle.ui.auth.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.test.nexle.R
import com.example.test.nexle.ui.auth.viewmodel.AuthViewModel
import com.example.test.nexle.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity: AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.login("test1@gmail.com", "12345678").observe(this, Observer {
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