package com.jrodriguezva.mifilmo.domain.model

data class Movie(
    val adult: Boolean?,
    val backdropPath: String?,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String,
    val voteAverage: Float,
    val voteCount: Int,
    var checked: Boolean = false,
    var homepage: String?,
    val runtime: Int?,
    val status: String?,
    val tagline: String?,
    val page: Int,
    var favorite: Boolean = false,
)
