package com.jrodriguezva.mifilmo.framework.network.user

import androidx.core.net.toUri
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.jrodriguezva.mifilmo.data.datasource.network.UserNetworkDataSource
import com.jrodriguezva.mifilmo.domain.model.User
import com.jrodriguezva.mifilmo.framework.mapper.toDomain
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserNetworkDataSourceImpl(
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage
) : UserNetworkDataSource {
    override fun getCurrentUser() = auth.currentUser?.toDomain()

    override suspend fun registerUser(email: String, password: String): User? {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await().user?.toDomain()
        } catch (expected: Exception) {
            null
        }
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

    override suspend fun deleteUser() = suspendCoroutine<Boolean> { continuation ->
        auth.currentUser?.run {
            delete().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(true)
                } else {
                    continuation.resume(false)
                }
            }
        } ?: continuation.resume(false)
    }

    override fun logout() {
        auth.signOut()
    }

    override suspend fun login(email: String, password: String): User? {
        return try {
            auth.signInWithEmailAndPassword(email, password).await().user?.toDomain()
        } catch (exception: FirebaseException) {
            null
        }
    }

    override suspend fun loginWithGoogle(token: String): User? {
        return try {
            val credential = GoogleAuthProvider.getCredential(token, null)
            auth.signInWithCredential(credential).await().user?.toDomain()
        } catch (exception: FirebaseException) {
            null
        }
    }
}