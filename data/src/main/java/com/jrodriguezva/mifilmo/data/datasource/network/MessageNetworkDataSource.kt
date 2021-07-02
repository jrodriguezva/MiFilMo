package com.jrodriguezva.mifilmo.data.datasource.network

import com.jrodriguezva.mifilmo.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageNetworkDataSource {
    fun getMessagesByMovie(movieId: Int, language: String): Flow<List<Message>>
    fun getMyMessagesByMovie(movieId: Int, language: String): Flow<List<Message>>
    suspend fun pushMessage(message: Message)

}
