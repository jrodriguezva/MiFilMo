package com.jrodriguezva.mifilmo.domain.usecase

import com.jrodriguezva.mifilmo.domain.repository.AuthRepository
import javax.inject.Inject

class Logout @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke() = repository.logout()
}