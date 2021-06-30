package com.jrodriguezva.mifilmo.data.datasource.network

import com.jrodriguezva.mifilmo.domain.model.User

interface UserNetworkDataSource {
    fun getCurrentUser(): User?
    suspend fun registerUser(email: String, password: String): User?
    suspend fun login(email: String, password: String): User?
    suspend fun loginWithGoogle(token: String): User?
    suspend fun updateUser(user: User)
    suspend fun deleteUser(): Boolean
    fun logout()
}
