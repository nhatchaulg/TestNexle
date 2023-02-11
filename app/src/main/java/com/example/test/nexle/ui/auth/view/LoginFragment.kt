package com.example.test.nexle.ui.auth.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.test.nexle.R
import com.example.test.nexle.databinding.FragmentLoginBinding
import com.example.test.nexle.ui.auth.viewmodel.AuthViewModel
import com.example.test.nexle.ui.base.view.BaseFragment
import com.example.test.nexle.utils.Status
import com.example.test.nexle.utils.UtilsSharePreference
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
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setupView()
    }

    override fun setupView() {
        _binding?.btnRegister?.setOnClickListener {
            val bundle = bundleOf("recipient" to "")
            navController.navigate(
                R.id.action_loginFragment_to_registerFragment,
                bundle
            )
        }

        _binding?.btnLogin?.setOnClickListener {
            if (checkValidate()) {
                val email = _binding?.etEmail?.text.toString()
                val password = _binding?.etPassword?.text.toString()
                login(email, password)
            }
        }

        _binding?.etPassword?.transformationMethod = AsteriskPasswordTransformationMethod()
    }

    private fun checkValidate(): Boolean {
        val isEmailValidate = checkEmailValidate()
        val isPasswordValidate = checkPasswordValidate()
        return isEmailValidate && isPasswordValidate
    }

    private fun checkPasswordValidate(): Boolean {
        if (_binding?.etPassword?.text.isNullOrEmpty()) {
            showPasswordValidate(getString(R.string.password_is_required))
            return false
        } else {
            val lengthPassword = _binding?.etPassword?.text?.length ?: 0
            if (lengthPassword < 6 || lengthPassword > 18) {
                showPasswordValidate(getString(R.string.password_length_validate))
                return false
            } else {
                val password = _binding?.etPassword?.text.toString()
                if (password.filter { it.isDigit() }.firstOrNull() == null) {
                    showPasswordValidate(getString(R.string.password_format_validate))
                    return false
                }

                if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) {
                    showPasswordValidate(getString(R.string.password_format_validate))
                    return false
                }
                if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) {
                    showPasswordValidate(getString(R.string.password_format_validate))
                    return false
                }
            }
        }
        return true
    }

    private fun showPasswordValidate(message: String) {
        _binding?.validatePassword?.visibility = View.VISIBLE
        _binding?.validatePassword?.text = message
    }

    private fun checkEmailValidate(): Boolean {
        if (_binding?.etEmail?.text.isNullOrEmpty()) {
            _binding?.validateEmail?.visibility = View.VISIBLE
            return false
        }
        return true
    }

    private fun login(email: String, password: String) {
        viewModel.login(email, password).observe(requireActivity()) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        UtilsSharePreference.updateUserToken(it.data?.token)
                        UtilsSharePreference.updateUserName(it.data?.displayName)
                        (activity as AuthActivity).startMainActivity()
                    }
                    Status.ERROR -> {
                        (activity as AuthActivity).getBinding().layoutLoading.visibility = View.GONE
                        Toast.makeText(context, "Login Fail", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        (activity as AuthActivity).getBinding().layoutLoading.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        _binding?.validateEmail?.visibility = View.GONE
        _binding?.validatePassword?.visibility = View.GONE
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) {}
}