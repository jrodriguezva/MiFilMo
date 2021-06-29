package com.jrodriguezva.mifilmo.domain.usecase

import com.jrodriguezva.mifilmo.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovie @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(id: Int) = repository.getMovieDetails(id)
}