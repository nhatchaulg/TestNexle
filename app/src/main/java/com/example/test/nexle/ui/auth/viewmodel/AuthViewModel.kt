package com.example.test.nexle.ui.auth.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.test.nexle.MyApplication
import com.example.test.nexle.R
import com.example.test.nexle.data.model.LoginRequest
import com.example.test.nexle.data.model.RegisterRequest
import com.example.test.nexle.data.repository.AuthRepository
import com.example.test.nexle.utils.Resource
import kotlinx.coroutines.Dispatchers

class AuthViewModel constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    var email: String = ""
    var password: String = ""
    var firstName: String = ""
    var lastName: String = ""

    val emailValidateMessage by lazy { ObservableField("") }
    val isValidateEmail by lazy { ObservableField(false) }

    val passwordValidateMessage by lazy { ObservableField("") }
    val isValidatePassword by lazy { ObservableField(false) }

    val firstNameValidateMessage by lazy { ObservableField("") }
    val isValidateFirstName by lazy { ObservableField(false) }

    val lastNameValidateMessage by lazy { ObservableField("") }
    val isValidateLastName by lazy { ObservableField(false) }

    val isShowLoading by lazy { ObservableField(false) }

    fun login() = liveData(Dispatchers.IO) {
        if (checkLoginValidate()) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = authRepository.login(LoginRequest(email, password))))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
    }

    private fun checkLoginValidate(): Boolean {
        val isEmailValidate = checkEmailValidate()
        val isPasswordValidate = checkPasswordValidate()
        return isEmailValidate && isPasswordValidate
    }

    private fun checkRegisterValidate(): Boolean {
        val isPasswordValidate = checkPasswordValidate()
        val isEmailValidate = checkEmailValidate()
        val isLastNameValidate = checkLastNameValidate()
        val isFirstNameValidate = checkFirstNameValidate()
        return isPasswordValidate && isEmailValidate && isFirstNameValidate && isLastNameValidate
    }

    private fun checkFirstNameValidate(): Boolean {
        if (firstName.isEmpty()) {
            showFirstNameValidate(
                MyApplication.mInstance?.getString(R.string.first_name_is_required) ?: ""
            )
            return false
        } else {
            if (firstName.length < 2) {
                showFirstNameValidate(
                    MyApplication.mInstance?.getString(R.string.first_name_least_2_characters) ?: ""
                )
                return false
            }
        }
        return true
    }

    private fun showFirstNameValidate(message: String) {
        isValidateFirstName.set(true)
        firstNameValidateMessage.set(message)
    }

    private fun checkLastNameValidate(): Boolean {
        if (lastName.isEmpty()) {
            showLastNameValidate(
                MyApplication.mInstance?.getString(R.string.last_name_is_required) ?: ""
            )
            return false
        } else {
            if (lastName.length < 2) {
                showLastNameValidate(
                    MyApplication.mInstance?.getString(R.string.last_name_least_2_characters) ?: ""
                )
                return false
            }
        }
        return true
    }

    private fun showLastNameValidate(message: String) {
        isValidateLastName.set(true)
        lastNameValidateMessage.set(message)
    }

    private fun checkEmailValidate(): Boolean {
        if (email.isEmpty()) {
            isValidateEmail.set(true)
            emailValidateMessage.set(
                MyApplication.mInstance?.getString(R.string.email_is_required) ?: ""
            )
            return false
        } else {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                isValidateEmail.set(true)
                emailValidateMessage.set(
                    MyApplication.mInstance?.getString(R.string.email_is_validate) ?: ""
                )
            }
        }
        return true
    }

    private fun checkPasswordValidate(): Boolean {
        if (password.isEmpty()) {
            showPasswordValidate(
                MyApplication.mInstance?.getString(R.string.password_is_required) ?: ""
            )
            return false
        } else {
            val lengthPassword = password.length
            if (lengthPassword < 6 || lengthPassword > 18) {
                showPasswordValidate(
                    MyApplication.mInstance?.getString(R.string.password_length_validate) ?: ""
                )
                return false
            } else {
                if (password.firstOrNull { it.isDigit() } == null) {
                    showPasswordValidate(
                        MyApplication.mInstance?.getString(R.string.password_format_validate) ?: ""
                    )
                    return false
                }

                if (password.filter { it.isLetter() }.firstOrNull { it.isLowerCase() } == null) {
                    showPasswordValidate(
                        MyApplication.mInstance?.getString(R.string.password_format_validate) ?: ""
                    )
                    return false
                }
                if (password.firstOrNull { !it.isLetterOrDigit() } == null) {
                    showPasswordValidate(
                        MyApplication.mInstance?.getString(R.string.password_format_validate) ?: ""
                    )
                    return false
                }
            }
        }
        return true
    }

    private fun showPasswordValidate(message: String) {
        isValidatePassword.set(true)
        passwordValidateMessage.set(message)
    }

    fun register() =
        liveData(Dispatchers.IO) {
            if (checkRegisterValidate()) {
                emit(Resource.loading(data = null))
                try {
                    emit(
                        Resource.success(
                            data = authRepository.register(
                                RegisterRequest(
                                    firstName,
                                    lastName,
                                    email,
                                    password
                                )
                            )
                        )
                    )
                } catch (exception: Exception) {
                    emit(
                        Resource.error(
                            data = null,
                            message = exception.message ?: "Error Occurred!"
                        )
                    )
                }
            }

        }
}