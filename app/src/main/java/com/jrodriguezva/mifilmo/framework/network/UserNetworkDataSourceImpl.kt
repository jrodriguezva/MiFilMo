package com.jrodriguezva.mifilmo.framework.network

import androidx.core.net.toUri
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.jrodriguezva.mifilmo.data.datasource.UserNetworkDataSource
import com.jrodriguezva.mifilmo.domain.model.User
import kotlinx.coroutines.tasks.await

class UserNetworkDataSourceImpl(
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage
) : UserNetworkDataSource {
    override fun getCurrentUser() = auth.currentUser?.toDomain()

    override suspend fun registerUser(email: String, password: String): User? {
        return auth.createUserWithEmailAndPassword(email, password).await().user?.toDomain()
    }

    override suspend fun updateUser(user: User) {
        val path = user.photoUrl?.toUri()
        val ref =
            storage.reference.child("images").child(user.uid).child("${path?.lastPathSegment}")
        path?.let { ref.putFile(it).await() }

        val profileUpdates = userProfileChangeRequest {
            displayName = user.name
            photoUri = ref.downloadUrl.await()
        }
        auth.currentUser?.updateProfile(profileUpdates)?.await()
    }

    override suspend fun login(email: String, password: String): User? {
        return try {
            auth.signInWithEmailAndPassword(email, password).await().user?.toDomain()
        } catch (exception: FirebaseException) {
            null
        }
    }
}