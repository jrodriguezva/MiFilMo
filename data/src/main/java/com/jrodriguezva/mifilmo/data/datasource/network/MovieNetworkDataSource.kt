package com.jrodriguezva.mifilmo.data.datasource.network

import com.jrodriguezva.mifilmo.domain.model.Movie
import com.jrodriguezva.mifilmo.domain.model.People
import com.jrodriguezva.mifilmo.domain.model.Person
import com.jrodriguezva.mifilmo.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface MovieNetworkDataSource {
    suspend fun discoverMovies(page: Int, sortBy: String, language: String): Resource<List<Movie>>
    suspend fun getMovieDetails(movieId: Int, language: String): Resource<Movie>
    suspend fun getCastByMovie(movieId: Int, language: String): Resource<List<People>>
    fun getFavoriteMovies(): Flow<Movie>
    fun saveFavoriteMovie(movie: Movie)
}
