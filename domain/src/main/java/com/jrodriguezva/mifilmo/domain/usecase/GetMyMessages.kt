package com.jrodriguezva.mifilmo.domain.usecase

import com.jrodriguezva.mifilmo.domain.repository.MessageRepository
import javax.inject.Inject

class GetMyMessages @Inject constructor(private val repository: MessageRepository) {
    operator fun invoke(movieId: Int) = repository.getMyMessagesByMovie(movieId)
}