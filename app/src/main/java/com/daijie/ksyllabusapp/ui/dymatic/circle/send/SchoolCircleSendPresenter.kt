package com.daijie.ksyllabusapp.ui.dymatic.circle.send

import com.daijie.ksyllabusapp.base.ISubscriptioner
import com.daijie.ksyllabusapp.base.Subscriptioner
import com.daijie.ksyllabusapp.data.SchoolCircleRequest
import com.daijie.ksyllabusapp.ext.post
import com.daijie.ksyllabusapp.ext.to_ui
import com.daijie.ksyllabusapp.repository.schoolcircle.SchoolCircleDataRepository
import com.daijie.ksyllabusapp.repository.user.UserDataRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-29.
 */
class SchoolCircleSendPresenter @Inject constructor(
        private val schoolCircleSendView: SchoolCircleSendContract.View,
        private val userDataRepository: UserDataRepository,
        private val schoolCircleDataRepository: SchoolCircleDataRepository,
        private val subscriptioner: Subscriptioner
) : SchoolCircleSendContract.Presenter, ISubscriptioner by subscriptioner {

    override fun subscribe() {

    }

    override fun postCircle(content: String, imageJson: String, source: String) {
        userDataRepository.currentUser.post(schoolCircleSendView) { user ->

            val request = if (source == "匿名") {
                SchoolCircleRequest(content, source, -1, "", imageJson)
            } else {
                SchoolCircleRequest(content, source, user.id, user.token, imageJson)
            }
            schoolCircleDataRepository.sendCircles(request)
                    .delay(3000, TimeUnit.MILLISECONDS)
                    .to_ui()
                    .doOnRequest {
                        schoolCircleSendView.showLoadingDialog("正在发布消息圈")
                    }
                    .subscribe({
                        schoolCircleSendView.hideLoadingDialog()
                        schoolCircleSendView.showSuccess(SchoolCircleSendContract.MESSAGE_SEND_CIRCLE_SUCCESS)
                    }, {
                        schoolCircleSendView.hideLoadingDialog()
                        schoolCircleSendView.showError(SchoolCircleSendContract.MESSAGE_SEND_CIRCLE_FAIL)
                    })
                    .let {
                        add(it)
                    }
        }
    }


    override fun unsubscribe() {
        dispose()
    }


}