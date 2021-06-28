package com.jrodriguezva.mifilmo.data.datasource.local

import com.jrodriguezva.mifilmo.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    fun getAllMovies(): Flow<List<Movie>>
    suspend fun insertMovies(movies: List<Movie>)
    suspend fun getLastPage(): Int
}