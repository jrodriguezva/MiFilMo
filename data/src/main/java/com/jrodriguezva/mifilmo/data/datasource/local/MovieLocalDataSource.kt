package com.jrodriguezva.mifilmo.data.datasource.local

import com.jrodriguezva.mifilmo.domain.model.Movie
import com.jrodriguezva.mifilmo.domain.model.People
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    fun getAllMovies(): Flow<List<Movie>>
    fun getMovie(movieId: Int): Flow<Movie>
    suspend fun getPeoplesByMovie(movieId: Int): List<People>

    suspend fun insertMovies(movies: List<Movie>)
    suspend fun getLastPage(): Int
    suspend fun insertPeopleWithMovie(movieId: Int, peoples: List<People>)
    suspend fun updateMovie(movieId: Int, data: Movie)
}