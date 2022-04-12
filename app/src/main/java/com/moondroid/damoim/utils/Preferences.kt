package com.moondroid.damoim.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.moondroid.damoim.model.User
import com.moondroid.damoim.utils.Constants.PREFS_NAME
import com.moondroid.damoim.utils.Constants.USER


class Preferences(context: Context) {

    val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun putLong(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }

    fun getLong(key: String): Long {
        return prefs.getLong(key, 0L)
    }

    fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return prefs.getString(key, "")
    }

    fun putBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return prefs.getBoolean(key, false)
    }

    fun putInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int {
        return prefs.getInt(key, -1)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }

    fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }

}