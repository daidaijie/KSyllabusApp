package com.daijie.ksyllabusapp.ui.dymatic.notice.list

import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.data.LoadDataStatus
import com.daijie.ksyllabusapp.data.SchoolDymaticData
import com.daijie.ksyllabusapp.ui.dymatic.base.list.SchoolDymaticPresenter
import com.daijie.ksyllabusapp.ext.post
import com.daijie.ksyllabusapp.ext.to_ui
import com.daijie.ksyllabusapp.repository.schooldymatic.SchoolDymaticDataRepository
import com.daijie.ksyllabusapp.repository.schoolnotice.SchoolNoticeDataRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/22.
 */
class SchoolNoticePresenter @Inject constructor(
        private val schoolNoticeView: SchoolNoticeContract.View,
        private val userDataRepository: UserDataRepository,
        schoolDymaticDataRepository: SchoolDymaticDataRepository,
        private val schoolNoticeDataRepository: SchoolNoticeDataRepository,
        subscriptioner: Subscriptioner
) : SchoolDymaticPresenter(schoolNoticeView, userDataRepository,
        schoolDymaticDataRepository, subscriptioner), SchoolNoticeContract.Presenter {

    override fun loadDatas(start: Int, limit: Int) {
        userDataRepository.currentUser.post(schoolNoticeView) { user ->
            schoolNoticeView.startLoading(LoadDataStatus.getRequestMethod(start))
            schoolNoticeDataRepository.getNotices(start + 1, limit)
                    .to_ui()
                    .map {
                        it.map {
                            val isMyPost = it.schoolDymaticUser.account == user.account
                            var isMyLove = false
                            it.schoolDymaticThumbUps?.let {
                                isMyLove = it.find {
                                    it.uid == user.id
                                } != null
                            }
                            SchoolDymaticData(it, isMyPost, user.level, isMyLove)
                        }
                    }
                    .subscribe({
                        if (it.isNotEmpty()) {
                            schoolNoticeView.showSuccess(LoadDataStatus.getSuccessMessage(start))
                            schoolNoticeView.appendDataList(it, start == 0)
                        } else {
                            schoolNoticeView.showSuccess(LoadDataStatus.getEmptyMessage(start))
                        }
                        schoolNoticeView.endLoading(LoadDataStatus.getRequestMethod(start))
                    }, {
                        if (schoolNoticeDataRepository.isSourceEmpty(it)) {
                            schoolNoticeView.showSuccess(LoadDataStatus.getEmptyMessage(start))
                        } else {
                            schoolNoticeView.showError(LoadDataStatus.getErrorMessage(start))
                        }
                        schoolNoticeView.endLoading(LoadDataStatus.getRequestMethod(start))
                    })
                    .let {
                        add(it)
                    }
        }
    }
}