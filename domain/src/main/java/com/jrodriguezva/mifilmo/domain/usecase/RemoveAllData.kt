package com.jrodriguezva.mifilmo.domain.usecase

import com.jrodriguezva.mifilmo.domain.repository.MovieRepository
import javax.inject.Inject

class RemoveAllData @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke() = repository.clearData()

}