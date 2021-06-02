package com.jrodriguezva.mifilmo.data.datasource

import com.jrodriguezva.mifilmo.domain.model.User

interface UserNetworkDataSource {
    fun getCurrentUser(): User?
    suspend fun registerUser(email: String, password: String): User?
    suspend fun login(email: String, password: String): User?
}
