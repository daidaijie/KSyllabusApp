package com.daijie.ksyllabusapp.ext

import java.util.*

/**
 * Created by daidaijie on 17-9-28.
 */
val Calendar.year
    get() = this[Calendar.YEAR]

val Calendar.month
    get() = this[Calendar.MONTH] + 1

val Calendar.date
    get() = this[Calendar.DAY_OF_MONTH]

