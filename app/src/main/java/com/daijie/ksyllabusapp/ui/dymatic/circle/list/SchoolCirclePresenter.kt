package com.daijie.ksyllabusapp.ui.dymatic.circle.list

import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.data.LoadDataStatus
import com.daijie.ksyllabusapp.data.SchoolDymaticData
import com.daijie.ksyllabusapp.ui.dymatic.base.list.SchoolDymaticPresenter
import com.daijie.ksyllabusapp.ext.post
import com.daijie.ksyllabusapp.ext.to_ui
import com.daijie.ksyllabusapp.repository.schoolcircle.SchoolCircleDataRepository
import com.daijie.ksyllabusapp.repository.schooldymatic.SchoolDymaticDataRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-22.
 */
class SchoolCirclePresenter @Inject constructor(
        private val schoolCircleView: SchoolCircleContract.View,
        private val userDataRepository: UserDataRepository,
        schoolDymaticDataRepository: SchoolDymaticDataRepository,
        private val schoolCircleDataRepository: SchoolCircleDataRepository,
        subscriptioner: Subscriptioner
) : SchoolDymaticPresenter(schoolCircleView, userDataRepository, schoolDymaticDataRepository, subscriptioner),
        SchoolCircleContract.Presenter {

    override fun loadDatas(beforeId: Int, limit: Int, page: Int) {
        userDataRepository.currentUser.post(schoolCircleView) { user ->
            schoolCircleView.startLoading(LoadDataStatus.getRequestMethod(page))
            schoolCircleDataRepository.getCircles(beforeId, limit)
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
                            schoolCircleView.showSuccess(LoadDataStatus.getSuccessMessage(page))
                            schoolCircleView.appendDataList(it, page == 0)
                        } else {
                            schoolCircleView.showSuccess(LoadDataStatus.getEmptyMessage(page))
                        }
                        schoolCircleView.endLoading(LoadDataStatus.getRequestMethod(page))
                    }, {
                        if (schoolCircleDataRepository.isSourceEmpty(it)) {
                            schoolCircleView.showSuccess(LoadDataStatus.getEmptyMessage(page))
                        } else {
                            schoolCircleView.showError(LoadDataStatus.getErrorMessage(page))
                        }
                        schoolCircleView.endLoading(LoadDataStatus.getRequestMethod(page))
                    })
                    .let {
                        add(it)
                    }
        }
    }
}