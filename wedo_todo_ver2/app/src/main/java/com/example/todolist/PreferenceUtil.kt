package com.example.todolist

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    var context = context
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun getIt(key: String, defValue: String): Context {
        return context
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

    fun delete(key: String, str: String){
        prefs.edit().remove(str).commit();
    }
}