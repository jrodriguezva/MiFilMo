package com.jrodriguezva.mifilmo.domain.usecase

import com.jrodriguezva.mifilmo.domain.repository.AuthRepository
import com.jrodriguezva.mifilmo.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteUser @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.deleteUser()
}