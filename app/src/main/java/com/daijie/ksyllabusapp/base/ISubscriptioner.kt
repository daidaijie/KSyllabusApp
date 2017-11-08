package com.daijie.ksyllabusapp.base

import rx.Subscription

/**
 * Created by daidaijie on 17-9-28.
 */
interface ISubscriptioner {

    fun add(subscription: Subscription)

    fun dispose()
}