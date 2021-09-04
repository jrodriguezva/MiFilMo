package com.jrodriguezva.mimofilms.data.remote


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreditsResult(
    @Json(name = "cast")
    val cast: List<CastResponse>,
    @Json(name = "crew")
    val crew: List<CrewResponse>,
    @Json(name = "id")
    val id: Int,
)

@JsonClass(generateAdapter = true)
data class CastResponse(
    @Json(name = "adult")
    val adult: Boolean,
    @Json(name = "cast_id")
    val castId: Int,
    @Json(name = "character")
    val character: String,
    @Json(name = "credit_id")
    val creditId: String,
    @Json(name = "gender")
    val gender: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "known_for_department")
    val knownForDepartment: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "order")
    val order: Int,
    @Json(name = "original_name")
    val originalName: String?,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "profile_path")
    val profilePath: String?,
)

@JsonClass(generateAdapter = true)
data class CrewResponse(
    @Json(name = "adult")
    val adult: Boolean,
    @Json(name = "credit_id")
    val creditId: String,
    @Json(name = "department")
    val department: String,
    @Json(name = "gender")
    val gender: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "job")
    val job: String,
    @Json(name = "known_for_department")
    val knownForDepartment: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "original_name")
    val originalName: String,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "profile_path")
    val profilePath: String?,
)
