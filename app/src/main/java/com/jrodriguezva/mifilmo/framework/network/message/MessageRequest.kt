package com.jrodriguezva.mifilmo.framework.network.message


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PushMessage(
    @Json(name = "to")
    val topic: String?,
    @Json(name = "data")
    val `data`: Data?,
)

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "movieId")
    val movieId: Int?,
    @Json(name = "userName")
    val userName: String?,
    @Json(name = "userId")
    val userId: String?
)

