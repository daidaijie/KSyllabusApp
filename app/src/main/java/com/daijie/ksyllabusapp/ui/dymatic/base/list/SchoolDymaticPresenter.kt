package com.daijie.ksyllabusapp.ui.dymatic.base.list

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.repository.schooldymatic.SchoolDymaticDataRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository

/**
 * Created by daidaijie on 2017/10/24.
 */
abstract class SchoolDymaticPresenter(
        private val schoolDymaticView: SchoolDymaticContract.View,
        private val userDataRepository: UserDataRepository,
        private val schoolDymaticDataRepository: SchoolDymaticDataRepository,
        private val subscriptioner: Subscriptioner
) : SchoolDymaticContract.Presenter, ISubscriptioner by subscriptioner {

    override fun subscribe() {

    }

    override fun unsubscribe() {
        dispose()
    }


}