package com.daijie.ksyllabusapp.repository.user

import cn.edu.stu.syllabus.di.scope.UserScoped
import com.daijie.ksyllabusapp.di.qualifier.Local
import com.daijie.ksyllabusapp.di.qualifier.Remote
import javax.inject.Inject

/**
 * Created by daidaijie on 17-9-28.
 */
@UserScoped
class UserDataRepository @Inject constructor(
        @Local private val userLocalDataSource: UserDataSource,
        @Remote private val userRemoteDataSource: UserDataSource
) : UserDataSource {

    override var currentUser: User?
//        get() = User("", "", "", 1, 1, "", "15cthuang", "Mandy123")
        get() = userLocalDataSource.currentUser
        set(value) {
            userLocalDataSource.currentUser = value
        }

    override fun loginUser(username: String, password: String) =
            userRemoteDataSource.loginUser(username, password)
                    .doOnNext {
                        currentUser = it
                    }
}