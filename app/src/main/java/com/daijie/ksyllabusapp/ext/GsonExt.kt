package com.daijie.ksyllabusapp.ext

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by daidaijie on 17-9-30.
 */


inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)