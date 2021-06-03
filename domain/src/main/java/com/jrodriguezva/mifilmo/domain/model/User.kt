package com.jrodriguezva.mifilmo.domain.model

data class User(
    val email: String?,
    var photoUrl: String?,
    var name: String?,
    val providerId: String,
    val uid: String
)
