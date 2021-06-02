package com.jrodriguezva.mifilmo.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrodriguezva.mifilmo.R
import com.jrodriguezva.mifilmo.domain.model.Resource
import com.jrodriguezva.mifilmo.domain.model.User
import com.jrodriguezva.mifilmo.domain.usecase.GetCurrentUserUseCase
import com.jrodriguezva.mifilmo.domain.usecase.LoginUseCase
import com.jrodriguezva.mifilmo.utils.ResourceProvider
import com.jrodriguezva.mifilmo.utils.ValidatorUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val loginUseCase: LoginUseCase,
    private val resourceProvider: ResourceProvider,
    private val validatorUtil: ValidatorUtil
) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _emailError = MutableStateFlow("")
    val emailError: StateFlow<String> get() = _emailError

    private val _loginError = MutableStateFlow("")
    val loginError: StateFlow<String> get() = _loginError

    private val _passError = MutableStateFlow("")
    val passError: StateFlow<String> get() = _passError

    private val _logged = MutableLiveData<User?>()
    val logged: LiveData<User?> = _logged

    init {
        viewModelScope.launch {
            getCurrentUserUseCase().collect {
                when (it) {
                    is Resource.Loading -> _loading.value = true
                    is Resource.Success -> {
                        Log.e("a", "${it.data}")
                        _logged.value = it.data
                        _loading.value = true
                    }
                    else -> {
                        Log.e("a", "$it")
                        delay(5_000)
                        _loading.value = false
                    }
                }
            }
        }
    }

    private fun clearErrors() {
        _emailError.value = ""
        _loginError.value = ""
        _passError.value = ""
    }

    fun signIn(email: String, password: String) {
        clearErrors()
        if (!checkForm(email, password)) return
        viewModelScope.launch {
            loginUseCase(email, password).collect {
                when (it) {
                    is Resource.Loading -> _loading.value = true
                    is Resource.Success -> {
                        Log.e("a", "${it.data}")
                        _logged.value = it.data
                        _loading.value = true
                    }
                    else -> {
                        _loading.value = false
                        _loginError.value =
                            resourceProvider.getString(R.string.error_authentication)
                    }
                }
            }
        }
    }

    private fun checkForm(email: String, password: String): Boolean {
        val errorEmail = validatorUtil.validateEmail(email)?.let {
            _emailError.value = it
            true
        } ?: false

        val errorPass = validatorUtil.validatePassword(password)?.let {
            _passError.value = it
            true
        } ?: false

        return !errorEmail && !errorPass
    }

}