package com.jrodriguezva.mifilmo.data.datasource.network

import com.jrodriguezva.mifilmo.domain.model.Movie
import com.jrodriguezva.mifilmo.domain.model.Resource

interface MovieNetworkDataSource {
    suspend fun discoverMovies(page: Int, sortBy: String, language: String): Resource<List<Movie>>
}
