package com.jrodriguezva.mifilmo.domain.model

data class People(
    val peopleId: Int,
    val name: String,
    val character: String,
    val profilePath: String?,
    val order: Int,
)