package com.jrodriguezva.mifilmo.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrodriguezva.mifilmo.domain.model.Resource
import com.jrodriguezva.mifilmo.domain.model.User
import com.jrodriguezva.mifilmo.domain.usecase.DeleteUser
import com.jrodriguezva.mifilmo.domain.usecase.GetCurrentUser
import com.jrodriguezva.mifilmo.domain.usecase.Logout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUser,
    private val deleteUser: DeleteUser,
    private val logout: Logout,
) : ViewModel() {

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> get() = _loading

    private val _deletedUser = MutableStateFlow(false)
    val deletedUser: StateFlow<Boolean> get() = _deletedUser

    private val _user = MutableSharedFlow<User>()
    val user: SharedFlow<User> = _user

    init {
        viewModelScope.launch {
            getCurrentUser().collect {
                when (it) {
                    is Resource.Loading -> _loading.value = true
                    is Resource.Success -> {
                        _user.emit(it.data)
                        delay(5_000)
                        _loading.value = false
                    }
                    else -> {
                        delay(1_000)
                        _loading.value = false
                    }
                }
            }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            _deletedUser.value = deleteUser()
        }
    }

    fun logoutAccount() {
        viewModelScope.launch {
            logout()
        }
    }
}
