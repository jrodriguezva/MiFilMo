package com.jrodriguezva.mifilmo.domain.model

import com.jrodriguezva.mifilmo.domain.model.exception.FailException

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val exception: FailException) : Resource<Nothing>()
}


