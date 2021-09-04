package com.jrodriguezva.mifilmo.framework.local.movie

import com.jrodriguezva.mifilmo.data.datasource.local.MovieLocalDataSource
import com.jrodriguezva.mifilmo.domain.model.Movie
import com.jrodriguezva.mifilmo.domain.model.People
import com.jrodriguezva.mifilmo.framework.local.MiFilMoDatabase
import com.jrodriguezva.mifilmo.framework.mapper.toDatabase
import com.jrodriguezva.mifilmo.framework.mapper.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MovieLocalDataSourceImpl(private val db: MiFilMoDatabase) : MovieLocalDataSource {
    private val movieDao = db.movieDao()
    private val peopleDao = db.peopleDao()
    private val moviePeopleDao = db.moviePeopleDao()

    override fun getAllMovies() = movieDao.getAll().map { movies -> movies.map { it.toDomain() } }
    override fun getAllFavoriteMovies() =
        movieDao.getAllFavorite().map { movies -> movies.map { it.toDomain() } }

    override fun getMovie(movieId: Int) = movieDao.findById(movieId).map { it.toDomain() }
    override suspend fun getPeoplesByMovie(movieId: Int) =
        moviePeopleDao.getPeoplesByMovie(movieId)?.run {
            people.map { people -> people.toDomain() }
        } ?: emptyList()

    override suspend fun updateMovie(movieId: Int, data: Movie) {
        val movie = data.toDatabase()
        val movieDB = movieDao.getById(movieId)
        if (movieDB != null) {
            movie.page = movieDB.page
            movie.favorite = data.favorite ?: movieDB.favorite
        } else {
            movie.page = -1
        }
        movieDao.insert(movie)
    }

    override suspend fun clearData(): Boolean = withContext(Dispatchers.IO) {
        db.clearAllTables()
        true
    }

    override suspend fun insertMovies(movies: List<Movie>) {
        movieDao.insert(movies.map { it.toDatabase() })
    }

    override suspend fun insertPeopleWithMovie(movieId: Int, peoples: List<People>) {
        peopleDao.insert(peoples.map { it.toDatabase() })
        moviePeopleDao.insert(movieId, peoples.map { it.peopleId })
    }

    override suspend fun getLastPage() = movieDao.getLastPage()
}