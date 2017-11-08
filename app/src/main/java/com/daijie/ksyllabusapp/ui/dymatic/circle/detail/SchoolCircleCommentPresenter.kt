package com.daijie.ksyllabusapp.ui.dymatic.circle.detail

import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.ui.dymatic.base.detail.SchoolDymaticCommentPresenter
import com.daijie.ksyllabusapp.repository.schooldymatic.SchoolDymaticDataRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-25.
 */
class SchoolCircleCommentPresenter @Inject constructor(
        private val schoolCircleCommentView: SchoolCircleCommentContract.View,
        userDataRepository: UserDataRepository,
        schoolDymaticDataRepository: SchoolDymaticDataRepository,
        subscriptioner: Subscriptioner
) : SchoolDymaticCommentPresenter(
        schoolCircleCommentView, userDataRepository, schoolDymaticDataRepository, subscriptioner
), SchoolCircleCommentContract.Presenter