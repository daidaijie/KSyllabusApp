package com.daijie.ksyllabusapp.ui.dymatic.base.delete

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.data.SchoolDymaticData
import com.daijie.ksyllabusapp.ui.dymatic.base.delete.SchoolDymaticDeleteContract.Companion.MESSAGE_DELETE_SCHOOL_DYMATIC_FAIL
import com.daijie.ksyllabusapp.ui.dymatic.base.delete.SchoolDymaticDeleteContract.Companion.MESSAGE_DELETE_SCHOOL_DYMATIC_SUCCESS
import com.daijie.ksyllabusapp.ext.post
import com.daijie.ksyllabusapp.ext.to_ui
import com.daijie.ksyllabusapp.repository.schooldymatic.SchoolDymaticDataRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-28.
 */
class SchoolDymaticiDeletePresenter @Inject constructor(
        private val schoolDymaticDeleteView: SchoolDymaticDeleteContract.View,
        private val userDataRepository: UserDataRepository,
        private val schoolDymaticDataRepository: SchoolDymaticDataRepository,
        private val subscriptioner: Subscriptioner
) : SchoolDymaticDeleteContract.Presenter, ISubscriptioner by subscriptioner {

    @Inject
    fun setPresenter() {
        schoolDymaticDeleteView.presenter = this
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        dispose()
    }

    override fun deleteDymatic(schoolDymaticData: SchoolDymaticData, position: Int) {
        userDataRepository.currentUser.post(schoolDymaticDeleteView) { user ->
            schoolDymaticDataRepository.deleteSchoolDymatic(user, schoolDymaticData.schoolNotice.id)
                    .to_ui()
                    .subscribe({
                        schoolDymaticDeleteView.removeDymaticItem(position)
                        schoolDymaticDeleteView.showSuccess(MESSAGE_DELETE_SCHOOL_DYMATIC_SUCCESS)
                    }, {
                        schoolDymaticDeleteView.showError(it?.message ?: MESSAGE_DELETE_SCHOOL_DYMATIC_FAIL)
                    })
                    .let {
                        add(it)
                    }
        }

    }
}