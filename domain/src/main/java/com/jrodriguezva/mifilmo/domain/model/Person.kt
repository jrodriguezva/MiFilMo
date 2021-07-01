package com.jrodriguezva.mifilmo.domain.model

import java.util.Date

data class Person(
    val adult: Boolean?,
    val alsoKnownAs: List<String?>?,
    val biography: String?,
    val birthday: Date?,
    val gender: Int?,
    val homepage: String?,
    val id: Int,
    val knownForDepartment: String?,
    val name: String,
    val placeOfBirth: String?,
    val popularity: Double?,
    val profilePath: String?
)
