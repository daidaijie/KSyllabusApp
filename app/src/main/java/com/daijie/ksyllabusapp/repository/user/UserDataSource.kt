package com.daijie.ksyllabusapp.repository.user

import rx.Observable

/**
 * Created by daidaijie on 17-9-28.
 */
interface UserDataSource {
    fun loginUser(username: String, password: String): Observable<User>

    var currentUser: User?
}