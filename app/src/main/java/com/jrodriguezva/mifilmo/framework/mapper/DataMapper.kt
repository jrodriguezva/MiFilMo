package com.jrodriguezva.mifilmo.framework.mapper

import com.google.firebase.auth.FirebaseUser
import com.jrodriguezva.mifilmo.domain.model.Movie
import com.jrodriguezva.mifilmo.domain.model.User
import com.jrodriguezva.mifilmo.framework.local.movie.MovieDB
import com.jrodriguezva.mifilmo.framework.network.movie.MovieResult


fun FirebaseUser.toDomain(): User = User(
    photoUrl = photoUrl.toString(),
    name = displayName,
    email = email,
    providerId = providerId,
    uid = uid,
)


fun MovieDB.toDomain() = Movie(
    adult = adult,
    backdropPath = backdropPath,
    id = movieId,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
    voteCount = voteCount,
    homepage = homepage,
    runtime = runtime,
    status = status,
    tagline = tagline,
    page = page,
)


fun Movie.toDatabase() = MovieDB(
    adult = adult,
    backdropPath = backdropPath,
    movieId = id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
    voteCount = voteCount,
    homepage = homepage,
    runtime = runtime,
    status = status,
    tagline = tagline,
    page = page,
)

fun MovieResult.toDomain(page: Int) = Movie(
    adult = this.adult,
    backdropPath = this.backdropPath,
    id = this.id,
    originalLanguage = this.originalLanguage.orEmpty(),
    originalTitle = this.originalTitle,
    overview = this.overview,
    popularity = this.popularity,
    posterPath = this.posterPath,
    releaseDate = this.releaseDate,
    title = this.title,
    voteAverage = this.voteAverage.toFloat(),
    voteCount = this.voteCount,
    homepage = this.homepage,
    runtime = this.runtime,
    status = this.status,
    tagline = this.tagline,
    page = page,
)
