package com.jrodriguezva.mifilmo.framework.network.message

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.jrodriguezva.mifilmo.data.datasource.network.MessageNetworkDataSource
import com.jrodriguezva.mifilmo.domain.model.Message
import com.jrodriguezva.mifilmo.framework.network.NetworkUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class MessageNetworkDataSourceImpl(
    private val api: FCMApi,
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseUser: FirebaseAuth,
    private val firebaseMessaging: FirebaseMessaging,
    private val networkUtils: NetworkUtils
) : MessageNetworkDataSource {

    companion object {
        private const val MESSAGE_CHILD = "messages"
    }

    @ExperimentalCoroutinesApi
    override fun getMessagesByMovie(movieId: Int, language: String) = callbackFlow {
        val myRef = firebaseDatabase.reference.child(MESSAGE_CHILD).child("$movieId")
        val listener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val items = dataSnapshot.children.mapNotNull { snapshot ->
                    snapshot.getValue(Message::class.java)
                }.filter { it.language == language }
                trySend(items)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        myRef.addValueEventListener(listener)
        awaitClose { myRef.removeEventListener(listener) }
    }

    @ExperimentalCoroutinesApi
    override fun getMyMessagesByMovie(movieId: Int, language: String) = callbackFlow {
        val myRef = firebaseDatabase.reference.child(MESSAGE_CHILD).child("$movieId")
        val listener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val items = dataSnapshot.children.mapNotNull { snapshot ->
                    snapshot.getValue(Message::class.java)
                }.filter {
                    it.language == language && firebaseUser.uid?.let { uid ->
                        uid == it.peopleUid
                    } ?: false
                }
                trySend(items)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        myRef.addValueEventListener(listener)
        awaitClose { myRef.removeEventListener(listener) }
    }

    override suspend fun pushMessage(message: Message): Boolean {
        message.peopleUid = firebaseUser.currentUser?.uid
        message.name = firebaseUser.currentUser?.displayName
        val movieId = message.movieId.toString()
        firebaseDatabase.reference.child(MESSAGE_CHILD).child(movieId).push().setValue(message)

        val topic = "$movieId-${message.language}"
        firebaseMessaging.subscribeToTopic(topic)
        return if (networkUtils.isNetworkAvailable())
            api.sendNotification(
                PushMessage(
                    "/topics/$topic",
                    Data(message.movieId, message.name, message.peopleUid)
                )
            ).run {
                isSuccessful
            }
        else true
    }
}