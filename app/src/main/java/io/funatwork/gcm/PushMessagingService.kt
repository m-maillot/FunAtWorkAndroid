package io.funatwork.gcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushMessagingService : FirebaseMessagingService() {

    val TAG = "FirebaseService"

    val parser = PushParser()
    val intentCreator = IntentCreator()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.from)

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            val data = remoteMessage.data
            Log.d(TAG, "Message data payload: " + data)
            val intent = intentCreator.create(parser.parse(data))
            if (intent != null) {
                sendBroadcast(intent)
            } else {
                Log.d(TAG, "Unknown type, no broadcast sent")
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification.body)
        }
    }
}
