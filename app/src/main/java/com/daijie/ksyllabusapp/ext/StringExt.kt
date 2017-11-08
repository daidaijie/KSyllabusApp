package com.daijie.ksyllabusapp.ext

/**
 * Created by daidaijie on 17-10-9.
 */
operator fun String.times(x: Int): String {
    val sb = StringBuilder()
    for (i in 1..x) {
        sb.append(this)
    }
    return sb.toString()
}

fun String.numOf(x: Char): Int {
    var sum = 0
    for (c in this) {
        if (c == x) {
            ++sum
        }
    }
    return sum
}