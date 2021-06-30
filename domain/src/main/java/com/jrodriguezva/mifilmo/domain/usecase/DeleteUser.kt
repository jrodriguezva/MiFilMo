package com.jrodriguezva.mifilmo.domain.usecase

import com.jrodriguezva.mifilmo.domain.repository.AuthRepository
import com.jrodriguezva.mifilmo.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteUser @Inject constructor(
    private val repository: AuthRepository,
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Boolean {
        movieRepository.clearData()
        return repository.deleteUser()
    }
}