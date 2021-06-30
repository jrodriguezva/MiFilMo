package com.jrodriguezva.mifilmo.domain.repository

import com.jrodriguezva.mifilmo.domain.model.Resource
import com.jrodriguezva.mifilmo.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getCurrentUser(): Flow<Resource<User>>
    fun registerUser(
        email: String,
        password: String,
        name: String,
        photoUrl: String?
    ): Flow<Resource<User>>

    fun login(email: String, password: String): Flow<Resource<User>>
    fun loginWithGoogle(token: String): Flow<Resource<User>>
    suspend fun deleteUser(): Boolean
    fun logout()
}