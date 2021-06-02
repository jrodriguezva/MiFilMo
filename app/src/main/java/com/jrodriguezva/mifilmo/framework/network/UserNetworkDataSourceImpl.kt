package com.jrodriguezva.mifilmo.framework.network

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.jrodriguezva.mifilmo.data.datasource.UserNetworkDataSource
import com.jrodriguezva.mifilmo.domain.model.User
import kotlinx.coroutines.tasks.await

class UserNetworkDataSourceImpl(private val auth: FirebaseAuth) : UserNetworkDataSource {
    override fun getCurrentUser() = auth.currentUser?.toDomain()

    override suspend fun registerUser(email: String, password: String): User? {
        return auth.createUserWithEmailAndPassword(email, password).await().user?.toDomain()
    }

    override suspend fun login(email: String, password: String): User? {
        return try {
            auth.signInWithEmailAndPassword(email, password).await().user?.toDomain()
        } catch (exception: FirebaseException) {
            null
        }
    }
}