package com.jrodriguezva.mifilmo.data.repository

import com.jrodriguezva.mifilmo.data.datasource.network.MessageNetworkDataSource
import com.jrodriguezva.mifilmo.data.datasource.preferences.PreferenceDataSource
import com.jrodriguezva.mifilmo.domain.model.Message
import com.jrodriguezva.mifilmo.domain.repository.MessageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class MessageRepositoryImpl(
    private val messageNetworkDataSource: MessageNetworkDataSource,
    private val preferenceDataSource: PreferenceDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MessageRepository {


    override fun getMessageByMovie(movieId: Int) =
        messageNetworkDataSource.getMessagesByMovie(movieId, preferenceDataSource.getLanguage())
            .flowOn(dispatcher)

    override fun getMyMessagesByMovie(movieId: Int) =
        messageNetworkDataSource.getMyMessagesByMovie(movieId, preferenceDataSource.getLanguage())
            .flowOn(dispatcher)

    override suspend fun pushMessage(message: Message) {
        message.language = preferenceDataSource.getLanguage()
        messageNetworkDataSource.pushMessage(message)
    }
}
