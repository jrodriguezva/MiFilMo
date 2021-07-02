package com.jrodriguezva.mifilmo.domain.usecase

import com.jrodriguezva.mifilmo.domain.model.Message
import com.jrodriguezva.mifilmo.domain.repository.MessageRepository
import javax.inject.Inject

class PushMessage @Inject constructor(private val repository: MessageRepository) {
    suspend operator fun invoke(message: Message) = repository.pushMessage(message)
}