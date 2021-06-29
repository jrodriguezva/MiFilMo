package com.jrodriguezva.mifilmo.framework.network.movie

import com.jrodriguezva.mimofilms.data.remote.CreditsResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("sort_by") sortBy: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
    ): Response<TMDBResult>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String,
    ): Response<MovieResult>

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id") id: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String,
    ): Response<CreditsResult>
}