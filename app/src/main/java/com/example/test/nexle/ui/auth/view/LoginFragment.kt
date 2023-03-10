package com.example.test.nexle.ui.auth.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.test.nexle.R
import com.example.test.nexle.databinding.FragmentLoginBinding
import com.example.test.nexle.ui.auth.viewmodel.AuthViewModel
import com.example.test.nexle.ui.base.view.BaseFragment
import com.example.test.nexle.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment(), TextWatcher {

    private lateinit var navController: NavController
    private val viewModel: AuthViewModel by viewModel()
    private var _binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        _binding?.viewModel = viewModel
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setupView()
    }

    override fun setupView() {
        _binding?.btnRegister?.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        _binding?.btnLogin?.setOnClickListener {
            viewModel.email = _binding?.etEmail?.text.toString()
            viewModel.password = _binding?.etPassword?.text.toString()
            login()
        }

        _binding?.etEmail?.addTextChangedListener(this)
        _binding?.etPassword?.addTextChangedListener(this)

        _binding?.etPassword?.transformationMethod = AsteriskPasswordTransformationMethod()
    }

    private fun login() {
        viewModel.login().observe(requireActivity()) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        (activity as AuthActivity).startMainActivity(it.data)
                    }
                    Status.ERROR -> {
                        (activity as AuthActivity).viewModel.isShowLoading.set(false)
                        Toast.makeText(context, "Login Fail", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        (activity as AuthActivity).viewModel.isShowLoading.set(true)
                    }
                }
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        viewModel.isValidateEmail.set(false)
        viewModel.isValidatePassword.set(false)
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) {}
}