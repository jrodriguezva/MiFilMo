package com.jrodriguezva.mifilmo.framework.network.message

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FCMApi {
    @POST("send")
    suspend fun sendNotification(
        @Body message: PushMessage
    ): Response<MessageResponse>

}