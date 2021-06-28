package com.jrodriguezva.mifilmo.framework.local.movie

import com.jrodriguezva.mifilmo.data.datasource.local.MovieLocalDataSource
import com.jrodriguezva.mifilmo.domain.model.Movie
import com.jrodriguezva.mifilmo.framework.mapper.toDatabase
import com.jrodriguezva.mifilmo.framework.mapper.toDomain
import kotlinx.coroutines.flow.map

class MovieLocalDataSourceImpl(db: MovieDatabase) : MovieLocalDataSource {
    private val movieDao = db.movieDao()

    override fun getAllMovies() = movieDao.getAll().map { movies -> movies.map { it.toDomain() } }


    override suspend fun insertMovies(movies: List<Movie>) {
        movieDao.insert(movies.map { it.toDatabase() })
    }

    override suspend fun getLastPage() = movieDao.getLastPage()
}