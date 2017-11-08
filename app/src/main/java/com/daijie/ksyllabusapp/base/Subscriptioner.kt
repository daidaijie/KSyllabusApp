package com.daijie.ksyllabusapp.base

import rx.Subscription
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by daidaijie on 17-9-28.
 */
class Subscriptioner @Inject constructor() : ISubscriptioner {

    override fun add(subscription: Subscription) {
        compositeSubscription.add(subscription)
    }

    override fun dispose() {
        compositeSubscription.unsubscribe()
    }

    private val compositeSubscription = CompositeSubscription()


}