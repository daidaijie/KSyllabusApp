package com.daijie.ksyllabusapp.ui.dymatic.base.like

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.data.SchoolDymaticThumbUp
import com.daijie.ksyllabusapp.data.SchoolNotice
import com.daijie.ksyllabusapp.ui.dymatic.base.like.SchoolDymaticLikeContract.Companion.MESSAGE_LIKE_ERROR
import com.daijie.ksyllabusapp.ui.dymatic.base.like.SchoolDymaticLikeContract.Companion.MESSAGE_UNLIKE_ERROR
import com.daijie.ksyllabusapp.ext.post
import com.daijie.ksyllabusapp.ext.to_ui
import com.daijie.ksyllabusapp.repository.schooldymatic.SchoolDymaticDataRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-27.
 */
class SchoolDymaticLikePresenter @Inject constructor(
        private val schoolDymaticLikeView: SchoolDymaticLikeContract.View,
        private val userDataRepository: UserDataRepository,
        private val schoolDymaticDataRepository: SchoolDymaticDataRepository,
        private val subscriptioner: Subscriptioner
) : SchoolDymaticLikeContract.Presenter, ISubscriptioner by subscriptioner {

    @Inject
    fun setPresenter() {
        schoolDymaticLikeView.presenter = this
    }

    override fun subscribe() {

    }


    override fun like(schoolNotice: SchoolNotice, callback: (isSuccess: Boolean) -> Unit) {
        userDataRepository.currentUser.post(schoolDymaticLikeView) { user ->
            schoolDymaticDataRepository.like(user, schoolNotice.id)
                    .to_ui()
                    .subscribe({
                        val thumbUps = schoolNotice.schoolDymaticThumbUps ?: mutableListOf()
                        thumbUps.add(SchoolDymaticThumbUp(user.id, it))
                        schoolNotice.schoolDymaticThumbUps = thumbUps
                        callback.invoke(true)
                        schoolDymaticLikeView.showSuccess(SchoolDymaticLikeContract.MESSAGE_LIKE_SUCCESS)
                    }, {
                        callback.invoke(false)
                        schoolDymaticLikeView.showError(it.message ?: MESSAGE_LIKE_ERROR)
                    })
                    .let {
                        add(it)
                    }
        }
    }

    override fun unlike(schoolNotice: SchoolNotice, callback: (isSuccess: Boolean) -> Unit) {
        userDataRepository.currentUser.post(schoolDymaticLikeView) { user ->

            val myThumbUp = (schoolNotice.schoolDymaticThumbUps ?: mutableListOf())
                    .find {
                        it.uid == user.id
                    }

            myThumbUp?.let { thumbUp ->
                schoolDymaticDataRepository.unlike(user, thumbUp.id)
                        .to_ui()
                        .subscribe({
                            val thumbUps = schoolNotice.schoolDymaticThumbUps ?: mutableListOf()
                            thumbUps.remove(thumbUp)
                            schoolNotice.schoolDymaticThumbUps = thumbUps
                            callback.invoke(true)
                            schoolDymaticLikeView.showSuccess(SchoolDymaticLikeContract.MESSAGE_UNLIKE_SUCCESS)
                        }, {
                            callback.invoke(false)
                            schoolDymaticLikeView.showError(it.message ?: MESSAGE_UNLIKE_ERROR)
                        })
                        .let {
                            add(it)
                        }
            }
        }
    }

    override fun unsubscribe() {
        dispose()
    }

}