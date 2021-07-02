package com.jrodriguezva.mifilmo.domain.repository

import com.jrodriguezva.mifilmo.domain.model.Message
import com.jrodriguezva.mifilmo.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getMessageByMovie(movieId: Int): Flow<List<Message>>
    suspend fun pushMessage(message: Message)
    fun getMyMessagesByMovie(movieId: Int): Flow<List<Message>>
}