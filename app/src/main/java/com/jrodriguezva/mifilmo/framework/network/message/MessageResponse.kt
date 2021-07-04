package com.jrodriguezva.mifilmo.framework.network.message

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageResponse(
    @Json(name = "message_id")
    val messageId: Long?
)