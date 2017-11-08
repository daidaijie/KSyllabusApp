package com.daijie.ksyllabusapp.ext

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.daijie.ksyllabusapp.utils.DefaultGson
import com.google.gson.Gson

/**
 * Created by daidaijie on 17-9-28.
 */
inline fun <reified T> SharedPreferences.read(key: String): T? {
    val data = this.getString(key, "")
    return if (data.isEmpty()) null else DefaultGson.gson.fromJson(data, T::class.java)
}

inline fun <reified T> SharedPreferences.read(key: String, defaultObject: T, writeDefault: Boolean = false): T {
    val data = this.getString(key, "")
    return if (!data.isEmpty()) DefaultGson.gson.fromJson(data, T::class.java) else defaultObject.apply {
        if (writeDefault) {
            this@read.write(key, defaultObject)
        }
    }
}

@SuppressLint("CommitPrefEdits")
fun <T> SharedPreferences.write(key: String, obj: T) {
    val editor = this.edit()
    editor.putString(key, DefaultGson.gson.toJson(obj))
    editor.apply()
}