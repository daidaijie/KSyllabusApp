package com.daijie.ksyllabusapp.repository.user

import com.google.gson.annotations.SerializedName


/**
 * Created by daidaijie on 17-9-28.
 */

data class User(
        @SerializedName("nickname") val nickname: String?, //漆黑之翼
        @SerializedName("image") val image: String?, //http://ow7nw2lyt.bkt.clouddn.com/b83f477526ac4f52942bb1c488282e75.jpeg
        @SerializedName("token") val token: String, //666078
        @SerializedName("id") val id: Int, //9
        @SerializedName("level") val level: Int, //2
        @SerializedName("profile") val profile: String?, //你个猪😂😂😂
        @SerializedName("account") val account: String, //13yjli3
        @SerializedName("password") var password: String
)

fun User?.get(onSuccess: ((user: User) -> Unit)? = null, onError: (() -> Unit)? = null) {
    if (this != null) {
        onSuccess?.invoke(this)
    } else {
        onError?.invoke()
    }
}