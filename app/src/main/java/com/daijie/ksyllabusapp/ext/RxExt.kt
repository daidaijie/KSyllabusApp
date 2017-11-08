package com.daijie.ksyllabusapp.ext

import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.base.ToLoginView
import com.daijie.ksyllabusapp.data.JsonList
import com.daijie.ksyllabusapp.data.JsonResponse
import com.daijie.ksyllabusapp.data.isSuccess
import rx.Observable

/**
 * Created by daidaijie on 17-9-29.
 */
fun <T> Observable<JsonResponse<T>>.takeData(): Observable<T> = this.flatMap {
    if (it.isSuccess) {
        return@flatMap Observable.just(it.data)
    } else {
        var msg = it.message
        when (it.code) {
            401 -> msg = getStringById(R.string.error_token)
        }
        return@flatMap Observable.error<T>(Throwable(msg))
    }
}

fun <T> Observable<JsonList<T>>.takeList(): Observable<List<T>> = this.map {
    it.list
}

fun <T> Observable<T>.from_computation(): Observable<T> = this.subscribeOn(rx.schedulers.Schedulers.computation())

fun <T> Observable<T>.from_io(): Observable<T> = this.subscribeOn(rx.schedulers.Schedulers.io())

fun <T> Observable<T>.to_ui(): Observable<T> = this.observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())

fun <T> Observable<T>.to_io(): Observable<T> = this.observeOn(rx.schedulers.Schedulers.io())

fun <T> Observable<T>.to_computation(): Observable<T> = this.observeOn(rx.schedulers.Schedulers.computation())

fun <T> Observable<T>.onErrorToLogin(view: ToLoginView): Observable<T> = this.doOnError {
    if (it.message == getStringById(R.string.error_token)) {
        view.toLoginAct()
    }
}