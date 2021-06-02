package com.jrodriguezva.mifilmo.domain.model

data class User(
    val photoUrl: String?,
    val name: String?,
    val email: String?,
    val phone: String?,
    val providerId: String,
    val uid: String
)
