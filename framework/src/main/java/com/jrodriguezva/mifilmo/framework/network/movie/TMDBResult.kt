package com.jrodriguezva.mifilmo.framework.network.movie


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TMDBResult(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<MovieResult>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int,
)