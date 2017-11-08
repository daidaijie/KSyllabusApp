package com.daijie.ksyllabusapp.ext

import java.util.*

/**
 * Created by daidaijie on 2017/10/4.
 */
fun parseZeroOne(maps: SortedMap<Int, Int>): String {
    val start = maps.firstKey()
    var end = start
    for ((k, v) in maps) {
        if (v == 1) {
            end = k
        }
    }
    if (start == end) {
        return "${start}周"
    } else {
        return "$start-${end}周(${if (start % 2 == 1) "单周" else "双周"})"
    }
}

fun parseAllOne(maps: SortedMap<Int, Int>): String {
    val start = maps.firstKey()
    val end = maps.lastKey()
    if (start == end) {
        return "${start}周"
    } else {
        return "$start-${end}周"
    }
}


fun parse(weeks: SortedMap<Int, Int>): MutableList<String> {
    val ans = mutableListOf<String>()
    var tmps = sortedMapOf<Int, Int>()
    var zeroOneTmps = sortedMapOf<Int, Int>()
    val iter = weeks.iterator()
    while (iter.hasNext()) {
        val (k, v) = iter.next()
        if (v == 1) {
            tmps.put(k, v)
        } else {
            // 少于2说明之前没有连续的1
            if (tmps.size < 2) {
                //则放入01存储
                tmps.put(k, v)
                zeroOneTmps.putAll(tmps)
            } else {
                //tmps有连续的1,则需要将tmps和zeroOne的都提取出来
                if (zeroOneTmps.size > 0) {
                    ans.add(parseZeroOne(zeroOneTmps))
                }
                ans.add(parseAllOne(tmps))
                //刷新01缓存
                zeroOneTmps = sortedMapOf()
            }
            // 刷新临时储存
            tmps = sortedMapOf()
        }
    }

    if (tmps.size > 0) {
        val allOne = tmps.all { (_, v) ->
            v == 1
        } && tmps.size > 1
        if (allOne) {
            if (zeroOneTmps.size > 0) {
                ans.add(parseZeroOne(zeroOneTmps))
            }
            ans.add(parseAllOne(tmps))
        } else {
            zeroOneTmps.putAll(tmps)
            ans.add(parseZeroOne(zeroOneTmps))
        }
    }
    return ans
}

fun toWeek(data: String): List<String> {
    val maps = sortedMapOf<Int, Int>()
    for ((index, c) in data.withIndex()) {
        maps.put(index + 1, c.toInt() - '0'.toInt())
    }

    val startIt = maps.iterator()
    while (startIt.hasNext()) {
        val (_, v) = startIt.next()
        if (v == 0) {
            startIt.remove()
        } else {
            break
        }
    }

    val endIt = maps.iterator()
    val lastZero = mutableSetOf<Int>()
    while (endIt.hasNext()) {
        val (k, v) = endIt.next()
        if (v == 0) {
            lastZero.add(k)
        } else {
            lastZero.clear()
        }
    }
    lastZero.forEach {
        maps.remove(it)
    }


    val seqZeroIt = maps.iterator()
    val seqZero = mutableSetOf<Int>()
    val seqZeroTmp = mutableSetOf<Int>()
    while (seqZeroIt.hasNext()) {
        val (k, v) = seqZeroIt.next()
        if (v == 0) {
            seqZeroTmp.add(k)
        } else {
            if (seqZeroTmp.size > 1) {
                seqZero.addAll(seqZeroTmp)
            }
            seqZeroTmp.clear()
        }
    }

    seqZero.forEach {
        maps.remove(it)
    }

    val weeklists = mutableListOf<SortedMap<Int, Int>>()
    val spIt = maps.iterator()
    var spItTemp = sortedMapOf<Int, Int>()
    var lastIndex = -1
    while (spIt.hasNext()) {
        val (k, v) = spIt.next()
        if (lastIndex == -1 || (lastIndex + 1) == k) {
            spItTemp.put(k, v)
        } else {
            if (spItTemp.size > 0) {
                weeklists.add(spItTemp)
                spItTemp = sortedMapOf()
            }
            spItTemp.put(k, v)
        }
        lastIndex = k
    }
    if (spItTemp.size > 0) {
        weeklists.add(spItTemp)
    }

    val ans = mutableListOf<String>()

    weeklists.forEach {
        ans.addAll(parse(it))
    }
    return ans
}

fun pullString(x: String): String {
    val sb = StringBuilder()
    val zeroNum = 16 - x.length
    for (i in 0 until zeroNum) {
        sb.append(0)
    }
    sb.append(x)
    return sb.toString()
}

fun parseWeekDay(weeks: Int) = parseWeekDay(weeks.toString(2))

fun parseWeekDay(weeks: String, separator: String = " ") = toWeek(pullString(weeks)).joinToString(separator = separator)