package com.jrodriguezva.mifilmo.domain.usecase

import com.jrodriguezva.mifilmo.domain.model.Movie
import com.jrodriguezva.mifilmo.domain.model.Resource
import com.jrodriguezva.mifilmo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverMoreMovies @Inject constructor(private val repository: MovieRepository) {

    companion object {
        const val sortBy: String = "popularity.desc"
    }

    operator fun invoke(fromInit: Boolean): Flow<Resource<List<Movie>>> {
        return repository.checkRequireNewPage(
            fromInit,
            sortBy,
        )
    }
}