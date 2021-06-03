package com.jrodriguezva.mifilmo.domain.usecase

import com.jrodriguezva.mifilmo.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(email: String, password: String, name: String, photoUrl: String?) =
        repository.registerUser(email, password, name, photoUrl)
}