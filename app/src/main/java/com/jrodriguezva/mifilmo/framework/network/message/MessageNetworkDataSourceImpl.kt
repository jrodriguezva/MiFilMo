package com.jrodriguezva.mifilmo.framework.network.message

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jrodriguezva.mifilmo.data.datasource.network.MessageNetworkDataSource
import com.jrodriguezva.mifilmo.domain.model.Message
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class MessageNetworkDataSourceImpl(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseUser: FirebaseAuth,
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

    override suspend fun pushMessage(message: Message) {
        message.peopleUid = firebaseUser.currentUser?.uid
        message.name = firebaseUser.currentUser?.displayName
        firebaseDatabase.reference.child(MESSAGE_CHILD).child(message.movieId.toString()).push()
            .setValue(message)

    }

}