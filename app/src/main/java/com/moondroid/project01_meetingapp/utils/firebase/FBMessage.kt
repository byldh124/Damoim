package com.moondroid.project01_meetingapp.utils.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.NotificationParam
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.usecase.profile.UpdateTokenUseCase
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.presentation.ui.splash.SplashActivity
import com.moondroid.project01_meetingapp.utils.ProfileHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FBMessage : FirebaseMessagingService() {

    companion object {
        const val CHANNEL_ID = "Damoim_push_channel"
        const val CHANNEL_NAME = "Damoim_message"

        const val PENDING_INTENT_REQUEST_CODE = 0x10
        const val NOTIFICATION_ID = 0x11
    }

    @Inject
    lateinit var updateTokenUseCase: UpdateTokenUseCase

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        try {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val builder: NotificationCompat.Builder =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        CHANNEL_ID,
                        CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    notificationManager.createNotificationChannel(channel)
                    NotificationCompat.Builder(this, CHANNEL_ID)
                } else {
                    NotificationCompat.Builder(this, CHANNEL_ID)
                }

            var notiTitle: String
            var notiText: String
            var data: String

            message.notification?.let {
                notiTitle = it.title.toString()
                notiText = it.body.toString()
            }

            message.data.let {
                notiTitle = it[NotificationParam.TITLE].toString()
                notiText = it[NotificationParam.BODY].toString()
                data = it[NotificationParam.DATA].toString()
            }

            builder.apply {
                setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.logo))
                setSmallIcon(R.drawable.logo)
                setContentTitle(notiTitle)
                setContentText(notiText)
                setAutoCancel(true)
            }

            val intent = Intent(this, SplashActivity::class.java)
                .apply {
                    action = Intent.ACTION_MAIN
                    addCategory(Intent.CATEGORY_LAUNCHER)
                    putExtra(NotificationParam.DATA, data)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }

            val pendingIntent =
                PendingIntent.getActivity(
                    baseContext,
                    PENDING_INTENT_REQUEST_CODE,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            builder.setContentIntent(pendingIntent)

            notificationManager.notify(NOTIFICATION_ID, builder.build())
        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun sendNewToken(token: String) {
        if (ProfileHelper.setProfile()) {
            CoroutineScope(Dispatchers.Main).launch {
                updateTokenUseCase(ProfileHelper.profile.id, token).collect { result ->
                    result.onError {
                        logException(it)
                    }
                }
            }
        }
    }
}