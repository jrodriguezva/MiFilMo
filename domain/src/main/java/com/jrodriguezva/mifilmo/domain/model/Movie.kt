package com.jrodriguezva.mifilmo.domain.model

data class Movie(
    val adult: Boolean? = null,
    val backdropPath: String? = null,
    val id: Int = -1,
    val originalLanguage: String = "",
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double = 0.0,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val title: String = "",
    val voteAverage: Float = 0f,
    val voteCount: Int = 0,
    var checked: Boolean = false,
    var homepage: String? = null,
    val runtime: Int? = null,
    val status: String? = null,
    val tagline: String? = null,
    val page: Int = -1,
    var favorite: Boolean? = null,
)
