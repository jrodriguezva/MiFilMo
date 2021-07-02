package com.jrodriguezva.mifilmo.domain.model

import java.util.Date

data class Message(
    var peopleUid: String? = "",
    var name: String? = "",
    val text: String = "",
    val date: Date = Date(),
    var language: String? = "",
    val movieId: Int = 0,
)