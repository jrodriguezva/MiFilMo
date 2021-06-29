package com.jrodriguezva.mifilmo.domain.repository

import com.jrodriguezva.mifilmo.domain.model.Movie
import com.jrodriguezva.mifilmo.domain.model.People
import com.jrodriguezva.mifilmo.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun discoverMovies(): Flow<List<Movie>>
    fun getMovieDetails(id: Int): Flow<Movie>

    fun getPeopleByMovie(movieId: Int): Flow<Resource<List<People>>>
    fun checkRequireNewPage(fromInit: Boolean, sortBy: String): Flow<Resource<List<Movie>>>

}