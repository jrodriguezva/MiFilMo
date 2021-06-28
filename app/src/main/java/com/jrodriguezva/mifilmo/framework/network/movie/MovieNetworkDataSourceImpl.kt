package com.jrodriguezva.mifilmo.framework.network.movie

import com.jrodriguezva.mifilmo.data.datasource.network.MovieNetworkDataSource
import com.jrodriguezva.mifilmo.domain.model.Movie
import com.jrodriguezva.mifilmo.domain.model.Resource
import com.jrodriguezva.mifilmo.domain.model.exception.BadRequest
import com.jrodriguezva.mifilmo.domain.model.exception.NoConnectionAvailable
import com.jrodriguezva.mifilmo.framework.mapper.toDomain
import com.jrodriguezva.mifilmo.framework.network.NetworkUtils

class MovieNetworkDataSourceImpl(
    private val api: TMDBApi,
    private val apiKey: String,
    private val networkUtils: NetworkUtils
) : MovieNetworkDataSource {

    override suspend fun discoverMovies(
        page: Int,
        sortBy: String,
        language: String
    ): Resource<List<Movie>> {
        if (!networkUtils.isNetworkAvailable()) return Resource.Failure(NoConnectionAvailable)
        return api.discoverMovies(sortBy, language, page, apiKey).run {
            if (isSuccessful) {
                body()?.let { body -> Resource.Success(body.results.map { it.toDomain(page) }) }
                    ?: Resource.Success(emptyList())
            } else {
                Resource.Failure(BadRequest)
            }
        }
    }

}