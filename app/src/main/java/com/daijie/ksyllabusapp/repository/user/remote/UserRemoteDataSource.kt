package com.daijie.ksyllabusapp.repository.user.remote

import com.daijie.ksyllabusapp.api.LoginApi
import com.daijie.ksyllabusapp.ext.from_io
import com.daijie.ksyllabusapp.ext.takeData
import com.daijie.ksyllabusapp.repository.user.User
import com.daijie.ksyllabusapp.repository.user.UserDataSource
import rx.Observable

/**
 * Created by daidaijie on 17-9-28.
 */
class UserRemoteDataSource(val loginApi: LoginApi) : UserDataSource {
    override var currentUser: User?
        get() = throw RuntimeException("Can't get schoolDymaticUser from remote")
        set(value) {
            throw RuntimeException("Can't set schoolDymaticUser to remote")
        }

    override fun loginUser(username: String, password: String): Observable<User> {
        return loginApi.login(username, password)
                .from_io()
                .takeData()
                .doOnNext {
                    it.password = password
                }
    }

}