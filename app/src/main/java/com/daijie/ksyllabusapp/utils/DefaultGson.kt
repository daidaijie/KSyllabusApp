package com.daijie.ksyllabusapp.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Created by daidaijie on 17-9-28.
 */
object DefaultGson {

    @JvmStatic
    val gson: Gson by lazy {
        GsonBuilder()
                .enableComplexMapKeySerialization()
                .create()
    }

    @JvmStatic
    val gsonForPrint: Gson by lazy {
        GsonBuilder()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .create()
    }
}