package com.example.todolist

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class NotificationHelper(base: Context?) : ContextWrapper(base) {

    //채널 변수 만들기
    private val channelID: String = "channelID"
    private val channelNm: String = "channelName"

    init {
        //안드로이드 버전이 오레오보다 크면
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            //채널 생성
            createChannel()
        }
    }

    //채널 생성 함수
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(){

        //객체 생성
        val channel: NotificationChannel =
            NotificationChannel(channelID, channelNm, NotificationManager.IMPORTANCE_DEFAULT)

        //설정
        channel.enableLights(true) //빛
        channel.enableVibration(true) //진동
        channel.lightColor = Color.RED
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        //생성
        getManager().createNotificationChannel(channel)
    }

    //NotificationManager 생성
    fun getManager(): NotificationManager {

        return getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    //Notification 설정
    fun getChannelNotification(title: String, message: String): NotificationCompat.Builder{

        return NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle(title) //제목
            .setContentText(message)//내용
            .setSmallIcon(R.drawable.ic_launcher_background) //아이콘
    }
}