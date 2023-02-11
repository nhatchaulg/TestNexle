package com.example.test.nexle.ui.auth.view

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.example.test.nexle.databinding.ActivityAuthBinding
import com.example.test.nexle.ui.main.view.MainActivity

class AuthActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityAuthBinding
    private var isHandleKeyBoard = true
    private var isShowKeyBoard = false

    fun getBinding(): ActivityAuthBinding {
        return _binding
    }

    private val keyboardLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        if (isHandleKeyBoard) {
            val r = Rect()
            _binding.container.getWindowVisibleDisplayFrame(r)

            val heightDiff: Int = _binding.container.rootView.height - r.height()
            if (heightDiff > 0.25 * _binding.container.rootView.height) {
                if (!isShowKeyBoard) {
                    _binding.imHeader.visibility = View.GONE
                    isShowKeyBoard = true
                }
            } else {
                if (isShowKeyBoard) {
                    _binding.imHeader.visibility = View.VISIBLE
                    isShowKeyBoard = false
                }
            }
        }
        isHandleKeyBoard = true
    }

    fun startMainActivity() {
        _binding.layoutLoading.visibility = View.GONE
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding.container.viewTreeObserver.removeGlobalOnLayoutListener(
            keyboardLayoutListener
        )
    }
}