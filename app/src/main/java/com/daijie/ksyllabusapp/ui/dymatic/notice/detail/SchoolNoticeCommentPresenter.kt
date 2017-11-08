package com.daijie.ksyllabusapp.ui.dymatic.notice.detail

import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.ui.dymatic.base.detail.SchoolDymaticCommentPresenter
import com.daijie.ksyllabusapp.repository.schooldymatic.SchoolDymaticDataRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-25.
 */
class SchoolNoticeCommentPresenter @Inject constructor(
        private val schoolNoticeCommentView: SchoolNoticeCommentContract.View,
        userDataRepository: UserDataRepository,
        schoolDymaticDataRepository: SchoolDymaticDataRepository,
        subscriptioner: Subscriptioner
) : SchoolDymaticCommentPresenter(
        schoolNoticeCommentView, userDataRepository, schoolDymaticDataRepository, subscriptioner
), SchoolNoticeCommentContract.Presenter {
}