package com.jrodriguezva.mifilmo.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jrodriguezva.mifilmo.R
import com.jrodriguezva.mifilmo.ui.main.MainActivity

class MyFirebaseService : FirebaseMessagingService() {

    private fun sendRegistrationToServer(token: String) {
        // TODO: send to server when necessary
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        sendRegistrationToServer(p0)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.e("MyFirebaseService", "message $message")
        if (message.data.isNotEmpty()) {
            val userId = message.data["userId"]
            val movieId = message.data["movieId"]?.toIntOrNull()
            val userName = message.data["userName"]
            if (userId != Firebase.auth.currentUser?.uid) sendNotification(
                getString(
                    R.string.fcm_message_description,
                    userName
                ), movieId ?: 0
            )
        }
    }


    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(messageBody: String, movieId: Int) {
        val args = Bundle()
        args.putInt("movieId", movieId)

        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.nav_main_graph)
            .setDestination(R.id.movieDetailFragment)
            .setComponentName(MainActivity::class.java)
            .setArguments(args)
            .createPendingIntent()


        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_video)
            .setContentTitle(getString(R.string.fcm_message_title))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                getString(R.string.fcm_fallback_notification_channel_label),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

}