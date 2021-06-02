package com.jrodriguezva.mifilmo.domain.model.exception

sealed class FailException {
    object BadRequest : FailException()
}