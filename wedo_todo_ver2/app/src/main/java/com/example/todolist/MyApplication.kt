package com.example.todolist

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
        private var instance: MyApplication ? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}