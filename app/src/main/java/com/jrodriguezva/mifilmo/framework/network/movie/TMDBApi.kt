package com.jrodriguezva.mifilmo.framework.network.movie

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("sort_by") sortBy: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
    ): Response<TMDBResult>


}