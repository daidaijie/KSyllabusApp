package com.daijie.ksyllabusapp.ui.dymatic.base.like

import android.widget.TextView
import com.daijie.ksyllabusapp.base.ShowStatusView
import com.daijie.ksyllabusapp.data.SchoolDymaticData
import com.like.LikeButton
import kotlin.properties.Delegates

/**
 * Created by daidaijie on 17-10-27.
 * SchoolDymaticLikeViewImpl实现了点赞操作的View该有的处理
 * 其状态回调可以通过传入ShowStatusView来完成外部View之后的操作处理
 */
class SchoolDymaticLikeViewImpl(val showStatusView: ShowStatusView? = null)
    : SchoolDymaticLikeContract.View {

    override var presenter: SchoolDymaticLikeContract.Presenter by Delegates.notNull()

    fun handle(schoolDymaticData: SchoolDymaticData,
               likeButton: LikeButton,
               likeTextView: TextView,
               isLike: Boolean) {
        likeButton.isEnabled = false
        if (isLike) {
            presenter.like(schoolDymaticData.schoolNotice) { isSuccess ->
                if (!isSuccess) {
                    likeButton.isLiked = false
                }
                likeTextView.text = "${schoolDymaticData.schoolNotice.schoolDymaticThumbUps?.size ?: 0}"
                schoolDymaticData.isMyLike = likeButton.isLiked
                likeButton.isEnabled = true
            }
        } else {
            presenter.unlike(schoolDymaticData.schoolNotice) { isSuccess ->
                if (!isSuccess) {
                    likeButton.isLiked = true
                }
                likeTextView.text = "${schoolDymaticData.schoolNotice.schoolDymaticThumbUps?.size ?: 0}"
                schoolDymaticData.isMyLike = likeButton.isLiked
                likeButton.isEnabled = true
            }
        }
    }

    override fun showSuccess(msg: String) {
        showStatusView?.showSuccess(msg)
    }

    override fun showError(msg: String) {
        showStatusView?.showError(msg)
    }
}