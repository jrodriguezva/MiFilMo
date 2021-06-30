package com.jrodriguezva.mifilmo.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrodriguezva.mifilmo.R
import com.jrodriguezva.mifilmo.domain.model.Resource
import com.jrodriguezva.mifilmo.domain.model.User
import com.jrodriguezva.mifilmo.domain.usecase.RegisterUser
import com.jrodriguezva.mifilmo.utils.ResourceProvider
import com.jrodriguezva.mifilmo.utils.ValidatorUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUser: RegisterUser,
    private val validatorUtil: ValidatorUtil,
    private val resourceProvider: ResourceProvider,
) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _emailError = MutableStateFlow("")
    val emailError: StateFlow<String> get() = _emailError

    private val _registerError = MutableStateFlow("")
    val registerError: StateFlow<String> get() = _registerError

    private val _passError = MutableStateFlow("")
    val passError: StateFlow<String> get() = _passError

    private val _nameError = MutableStateFlow("")
    val nameError: StateFlow<String> get() = _nameError

    private var photo: String? = null


    private val _registered = MutableSharedFlow<User>()
    val registered: SharedFlow<User> = _registered


    private fun clearErrors() {
        _emailError.value = ""
        _registerError.value = ""
        _nameError.value = ""
        _passError.value = ""
    }


    private fun checkForm(email: String, password: String, name: String): Boolean {
        val errorEmail = validatorUtil.validateEmail(email)?.let {
            _emailError.value = it
            true
        } ?: false

        val errorPass = validatorUtil.validatePassword(password)?.let {
            _passError.value = it
            true
        } ?: false

        val errorName = validatorUtil.validateName(name)?.let {
            _nameError.value = it
            true
        } ?: false

        return !errorEmail && !errorPass && !errorName
    }

    fun signUp(email: String, password: String, name: String) {
        clearErrors()
        if (!checkForm(email, password, name)) return
        viewModelScope.launch {
            registerUser(email, password, name, photo).collect {
                when (it) {
                    is Resource.Loading -> _loading.value = true
                    is Resource.Success -> {
                        Log.e("a", "${it.data}")
                        _registered.emit(it.data)
                        _loading.value = false
                    }
                    is Resource.Failure -> {
                        _registerError.value = resourceProvider.getString(R.string.error_register)
                        _loading.value = false
                    }
                }
            }
        }
    }

    fun setProfileImage(encodeImage: String) {
        photo = encodeImage
    }
}