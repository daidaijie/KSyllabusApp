package com.daijie.ksyllabusapp.repository.user.local

import android.content.Context
import android.content.SharedPreferences
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.ext.month
import com.daijie.ksyllabusapp.ext.read
import com.daijie.ksyllabusapp.ext.write
import com.daijie.ksyllabusapp.ext.year
import com.daijie.ksyllabusapp.repository.semester.Semester
import com.daijie.ksyllabusapp.repository.semester.local.SemesterLocalDataSource
import com.daijie.ksyllabusapp.repository.user.User
import com.daijie.ksyllabusapp.repository.user.UserDataSource
import com.daijie.ksyllabusapp.utils.TimeUtils
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by daidaijie on 17-9-28.
 */
class UserLocalDataSource : UserDataSource {

    override var currentUser: User? by CurrentUserDelegate()

    companion object {
        const val EXTRA_CURRENT_USER = "CurrentUser"
    }

    override fun loginUser(username: String, password: String)
            = throw RuntimeException("Can't login from local")

    private class CurrentUserDelegate
        : ReadWriteProperty<Any?, User?> {

        private var value: User? = null

        private val userPreferences: SharedPreferences by lazy {
            App.context.getSharedPreferences("SchoolDymaticUser", Context.MODE_PRIVATE)
        }

        override fun getValue(thisRef: Any?, property: KProperty<*>): User?
                = value ?: userPreferences.read<User>(EXTRA_CURRENT_USER)

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: User?) {
            this.value = value?.apply {
                userPreferences.write(EXTRA_CURRENT_USER, value)
            }
        }
    }
}