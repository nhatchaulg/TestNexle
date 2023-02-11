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
import com.example.test.nexle.databinding.FragmentRegisterBinding
import com.example.test.nexle.ui.auth.viewmodel.AuthViewModel
import com.example.test.nexle.ui.base.view.BaseFragment
import com.example.test.nexle.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment(), TextWatcher {

    lateinit var navController: NavController
    private val viewModel: AuthViewModel by viewModel()
    private var _binding: FragmentRegisterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setupView()
    }

    override fun setupView() {
        _binding?.btnLogin?.setOnClickListener {
            val bundle = bundleOf("recipient" to "")
            navController.navigate(
                R.id.action_registerFragment_to_loginFragment,
                bundle
            )
        }

        _binding?.btnRegister?.setOnClickListener {
            if (checkValidate()) {
                val firstName = _binding?.etFirstName?.text.toString()
                val lastName = _binding?.etLastName?.text.toString()
                val mail = _binding?.etEmail?.text.toString()
                val password = _binding?.etPassword?.text.toString()
                register(firstName, lastName, mail, password)
            }
        }

        _binding?.etPassword?.transformationMethod = AsteriskPasswordTransformationMethod()

        _binding?.etFirstName?.addTextChangedListener(this)
        _binding?.etLastName?.addTextChangedListener(this)
        _binding?.etEmail?.addTextChangedListener(this)
        _binding?.etPassword?.addTextChangedListener(this)

    }

    private fun register(firstName: String, lastName: String, email: String, password: String) {
        viewModel.register(firstName, lastName, email, password).observe(requireActivity()) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        (activity as AuthActivity).startMainActivity()
                    }
                    Status.ERROR -> {
                        (activity as AuthActivity).getBinding().layoutLoading.visibility = View.GONE
                        Toast.makeText(context, "SignUp Fail", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        (activity as AuthActivity).getBinding().layoutLoading.visibility =
                            View.VISIBLE
                    }
                }
            }
        }
    }

    private fun checkValidate(): Boolean {
        val isPasswordValidate = checkValidatePassword()
        val isEmailValidate = checkEmailValidate()
        val isLastNameValidate = checkLastNameValidate()
        val isFirstNameValidate = checkFirstNameValidate()
        return isPasswordValidate && isEmailValidate && isFirstNameValidate && isLastNameValidate
    }

    private fun checkFirstNameValidate(): Boolean {
        if (_binding?.etFirstName?.text.isNullOrEmpty()) {
            showFirstNameValidate(getString(R.string.first_name_is_required))
            return false
        } else {
            if (_binding?.etFirstName?.text?.length!! < 2) {
                showFirstNameValidate(getString(R.string.first_name_least_2_characters))
                return false
            }
        }
        return true
    }

    private fun showFirstNameValidate(message: String) {
        _binding?.validateFirstName?.visibility = View.VISIBLE
        _binding?.validateFirstName?.text = message
    }

    private fun checkLastNameValidate(): Boolean {
        if (_binding?.etLastName?.text.isNullOrEmpty()) {
            showLastNameValidate(getString(R.string.last_name_is_required))
            return false
        } else {
            if (_binding?.etLastName?.text?.length!! < 2) {
                showLastNameValidate(getString(R.string.last_name_least_2_characters))
                return false
            }
        }
        return true
    }

    private fun showLastNameValidate(message: String) {
        _binding?.validateLastName?.visibility = View.VISIBLE
        _binding?.validateLastName?.text = message
    }

    private fun checkEmailValidate(): Boolean {
        if (_binding?.etEmail?.text.isNullOrEmpty()) {
            _binding?.validateEmail?.visibility = View.VISIBLE
            return false
        }
        return true
    }

    private fun checkValidatePassword(): Boolean {
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

                if (password.filter { it.isLetter() }.filter { it.isLowerCase() }
                        .firstOrNull() == null) {
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

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        _binding?.validateEmail?.visibility = View.GONE
        _binding?.validatePassword?.visibility = View.GONE
        _binding?.validateFirstName?.visibility = View.GONE
        _binding?.validateLastName?.visibility = View.GONE
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) {}
}