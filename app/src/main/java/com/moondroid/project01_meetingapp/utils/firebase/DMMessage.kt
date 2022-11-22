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
import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.data.response.BaseResponse
import com.moondroid.project01_meetingapp.domain.model.DMUser
import com.moondroid.project01_meetingapp.data.datasource.remote.ApiInterface
import com.moondroid.project01_meetingapp.data.datasource.local.realm.DMRealm
import com.moondroid.project01_meetingapp.presentation.view.splash.SplashActivity
import com.moondroid.project01_meetingapp.utils.DMLog
import com.moondroid.project01_meetingapp.utils.NotificationParam
import com.moondroid.project01_meetingapp.utils.RequestParam
import com.moondroid.project01_meetingapp.utils.log
import dagger.hilt.android.AndroidEntryPoint
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class DMMessage : FirebaseMessagingService() {

    private val channelId = "channel_dm"

    @Inject
    lateinit var retrofit: Retrofit

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
                        channelId,
                        "push ch ",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    notificationManager.createNotificationChannel(channel)
                    NotificationCompat.Builder(this, channelId)
                } else {
                    NotificationCompat.Builder(this, channelId)
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
                PendingIntent.getActivity(baseContext, 100, intent, PendingIntent.FLAG_IMMUTABLE)
            builder.setContentIntent(pendingIntent)

            notificationManager.notify(11, builder.build())
        } catch (e: Exception) {
            DMCrash.logException(e)
        }
    }

    private fun sendNewToken(token: String) {
        try {
            val items: RealmResults<DMUser> = DMRealm.getInstance().query<DMUser>().find()
            if (items.isNotEmpty()) {
                val user: DMUser = items.last()

                val body = JsonObject()

                body.addProperty(RequestParam.ID, user.id)
                body.addProperty(RequestParam.TOKEN, token)

                retrofit.create(ApiInterface::class.java).updateTokenService(body)
                    .enqueue(object : Callback<BaseResponse> {
                        override fun onResponse(
                            call: Call<BaseResponse>,
                            response: Response<BaseResponse>
                        ) {
                            log("sendNewToken() api call")
                        }

                        override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                            DMCrash.logException(t)
                        }
                    })
            }

        } catch (e: Exception) {
            DMCrash.logException(e)
        }

    }
}