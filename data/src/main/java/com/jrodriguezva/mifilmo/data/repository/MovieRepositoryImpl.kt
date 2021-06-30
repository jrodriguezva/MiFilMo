package com.jrodriguezva.mifilmo.data.repository

import com.jrodriguezva.mifilmo.data.datasource.local.MovieLocalDataSource
import com.jrodriguezva.mifilmo.data.datasource.network.MovieNetworkDataSource
import com.jrodriguezva.mifilmo.data.datasource.preferences.PreferenceDataSource
import com.jrodriguezva.mifilmo.domain.model.Movie
import com.jrodriguezva.mifilmo.domain.model.Resource
import com.jrodriguezva.mifilmo.domain.model.exception.EmptyCache
import com.jrodriguezva.mifilmo.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepositoryImpl(
    private val movieNetworkDataSource: MovieNetworkDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val preferenceDataSource: PreferenceDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MovieRepository {

    override fun checkRequireNewPage(fromInit: Boolean, sortBy: String) = flow {
        emit(Resource.Loading)
        val lastPage = movieLocalDataSource.getLastPage()
        val page = if (lastPage == -1) 1 else lastPage + 1
        if (!fromInit || page == 1) {
            when (val result = movieNetworkDataSource.discoverMovies(
                page,
                sortBy,
                preferenceDataSource.getLanguage()
            )) {
                is Resource.Success -> {
                    movieLocalDataSource.insertMovies(result.data)
                    emit(result)
                }
                else -> emit(result)
            }
        } else {
            emit(Resource.Failure(EmptyCache))
        }
    }.flowOn(dispatcher)

    override suspend fun clearData() = movieLocalDataSource.clearData()

    override suspend fun getFavoriteMovies() {
        try {
            movieNetworkDataSource.getFavoriteMovies().collect {
                movieLocalDataSource.updateMovie(it.id, it)
            }
        } catch (expected: Exception) {
        }
    }


    override fun getMovieDetails(id: Int) = movieLocalDataSource.getMovie(id).flowOn(dispatcher)

    private suspend fun updateMovieDetails(movieId: Int) {
        when (val result =
            movieNetworkDataSource.getMovieDetails(movieId, preferenceDataSource.getLanguage())) {
            is Resource.Success -> {
                movieLocalDataSource.updateMovie(movieId, result.data)
            }
        }
    }

    override fun getPeopleByMovie(movieId: Int) = flow {
        emit(Resource.Loading)
        updateMovieDetails(movieId)
        when (val result =
            movieNetworkDataSource.getPeopleByMovie(movieId, preferenceDataSource.getLanguage())) {
            is Resource.Success -> {
                movieLocalDataSource.insertPeopleWithMovie(movieId, result.data)
                emit(result)
            }
            else -> {
                val peoplesByMovie = movieLocalDataSource.getPeoplesByMovie(movieId)
                if (peoplesByMovie.isEmpty()) {
                    emit(Resource.Failure(EmptyCache))
                } else {
                    emit(Resource.Success(peoplesByMovie))
                }
            }
        }
    }.flowOn(dispatcher)

    override fun discoverMovies() = movieLocalDataSource.getAllMovies().flowOn(dispatcher)

    override fun discoverFavoriteMovies() =
        movieLocalDataSource.getAllFavoriteMovies().flowOn(dispatcher)

    override suspend fun saveFavoriteMovie(movie: Movie) {
        movieLocalDataSource.updateMovie(movie.id, movie)
        movieNetworkDataSource.saveFavoriteMovie(movie)
    }


}
