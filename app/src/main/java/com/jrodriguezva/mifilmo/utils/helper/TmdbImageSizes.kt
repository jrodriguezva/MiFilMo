package com.jrodriguezva.mifilmo.utils.helper

object TmdbImageSizes {

    const val baseImageUrl = "https://image.tmdb.org/t/p/"

    val backdropSizes = listOf(
        "w300",
        "w780",
        "w1280",
        "original"
    )

    val logoSizes = listOf(
        "w45",
        "w92",
        "w154",
        "w185",
        "w300",
        "w500",
        "original"
    )

    val posterSizes = listOf(
        "w92",
        "w154",
        "w185",
        "w342",
        "w500",
        "w780",
        "original"
    )

    val profileSizes = listOf(
        "w45",
        "w185",
        "h632",
        "original"
    )
}
