package com.jrodriguezva.mifilmo.domain.usecase

import com.jrodriguezva.mifilmo.domain.model.Movie
import com.jrodriguezva.mifilmo.domain.repository.MovieRepository
import javax.inject.Inject

class SaveFavoriteMovie @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(movie: Movie) = repository.saveFavoriteMovie(movie)
}