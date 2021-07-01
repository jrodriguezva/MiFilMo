package com.jrodriguezva.mifilmo.domain.usecase

import com.jrodriguezva.mifilmo.domain.repository.MovieRepository
import javax.inject.Inject

class GetCastByMovie @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(id: Int) = repository.getCastByMovie(id)
}