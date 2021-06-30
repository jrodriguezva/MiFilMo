package com.jrodriguezva.mifilmo.framework.local.movie.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieDB(
    @PrimaryKey val movieId: Int,
    val adult: Boolean?,
    val title: String,
    val overview: String?,
    val releaseDate: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val originalLanguage: String,
    val originalTitle: String?,
    val popularity: Double,
    val voteAverage: Float,
    val voteCount: Int,
    var homepage: String?,
    val runtime: Int?,
    val status: String?,
    val tagline: String?,
    var page: Int,
    var favorite: Boolean?,
)