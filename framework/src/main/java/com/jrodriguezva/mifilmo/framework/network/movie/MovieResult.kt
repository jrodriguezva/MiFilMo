package com.jrodriguezva.mifilmo.framework.network.movie


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResult(
    @Json(name = "adult")
    val adult: Boolean?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "budget")
    val budget: Int = 0,
    @Json(name = "genres")
    val genres: List<Genre> = emptyList(),
    @Json(name = "homepage")
    val homepage: String?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "imdb_id")
    val imdbId: String?,
    @Json(name = "original_language")
    val originalLanguage: String?,
    @Json(name = "original_title")
    val originalTitle: String?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "popularity")
    val popularity: Double = 0.0,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany> = emptyList(),
    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountry> = emptyList(),
    @Json(name = "release_date")
    val releaseDate: String?,
    @Json(name = "revenue")
    val revenue: Int = 0,
    @Json(name = "runtime")
    val runtime: Int?,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage> = emptyList(),
    @Json(name = "status")
    val status: String?,
    @Json(name = "tagline")
    val tagline: String?,
    @Json(name = "title")
    val title: String,
    @Json(name = "video")
    val video: Boolean = false,
    @Json(name = "vote_average")
    val voteAverage: Double = 0.0,
    @Json(name = "vote_count")
    val voteCount: Int = 0,
)

@JsonClass(generateAdapter = true)
data class Genre(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String?,
)

@JsonClass(generateAdapter = true)
data class ProductionCompany(
    @Json(name = "id")
    val id: Int,
    @Json(name = "logo_path")
    val logoPath: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "origin_country")
    val originCountry: String?,
)

@JsonClass(generateAdapter = true)
data class ProductionCountry(
    @Json(name = "iso_3166_1")
    val iso31661: String?,
    @Json(name = "name")
    val name: String?,
)

@JsonClass(generateAdapter = true)
data class SpokenLanguage(
    @Json(name = "english_name")
    val englishName: String?,
    @Json(name = "iso_639_1")
    val iso6391: String?,
    @Json(name = "name")
    val name: String?,
)
