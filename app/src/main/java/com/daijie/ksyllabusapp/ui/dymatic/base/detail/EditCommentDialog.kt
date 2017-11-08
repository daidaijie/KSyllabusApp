package com.daijie.ksyllabusapp.ui.dymatic.base.detail

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.base.BaseBottomDialog
import com.daijie.ksyllabusapp.data.SchoolDymaticCommentData
import com.daijie.ksyllabusapp.data.name
import com.daijie.ksyllabusapp.ext.getEnterCount
import com.daijie.ksyllabusapp.ext.hideInput
import com.daijie.ksyllabusapp.ext.showInput
import com.daijie.ksyllabusapp.utils.DrawableTintUtils
import com.daijie.ksyllabusapp.widgets.MaxLinesTextWatcher
import kotlinx.android.synthetic.main.dialog_edit_comment.view.*
import kotlinx.android.synthetic.main.view_hint_edit_text.view.*

/**
 * Created by daidaijie on 17-10-25.
 */
class EditCommentDialog : BaseBottomDialog() {

    companion object {
        const val DIALOG_EDIT_COMMENT = "EditCommentDialog"
        const val EXTRA_IS_COMMENT_OTHER = "isCommentOther"
        const val EXTRA__OTHER_COMMENT_DATA = "otherCommentData"
        const val MAX_LENGTH = 140
        const val MAX_LINE = 10


        @JvmStatic
        fun newInstance(isCommentOther: Boolean, otherCommentData: SchoolDymaticCommentData? = null)
                = EditCommentDialog().apply {
            val arg = Bundle()
            arg.putBoolean(EXTRA_IS_COMMENT_OTHER, isCommentOther)
            if (otherCommentData != null) {
                arg.putParcelable(EXTRA__OTHER_COMMENT_DATA, otherCommentData)
            }
            arguments = arg
        }
    }

    override val contentViewId = R.layout.dialog_edit_comment

    private val sendEnableIcon: Drawable by lazy {
        DrawableTintUtils.getTintDrawableByColorRes(R.drawable.ic_send, R.color.colorPrimary_Blue)
    }

    private val sendDisableIcon: Drawable by lazy {
        DrawableTintUtils.getTintDrawableByColorRes(R.drawable.ic_send, R.color.colorTextDisable)
    }

    private var onCommentSendListener: OnCommentSendListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnCommentSendListener) {
            onCommentSendListener = context
        } else {
            throw IllegalArgumentException("Activity must be implements OnCommentSendListener.")
        }
    }

    private val isCommentOther: Boolean by lazy {
        arguments.getBoolean(EXTRA_IS_COMMENT_OTHER, false)
    }

    private val otherCommentData: SchoolDymaticCommentData? by lazy {
        arguments.getParcelable<SchoolDymaticCommentData?>(EXTRA__OTHER_COMMENT_DATA)
    }

    @SuppressLint("SetTextI18n")
    override fun initView(view: View, savedInstanceState: Bundle?) {
        fun setLengthText() {
            val textNum = view.commentEditText.text.length
            val lineCount = view.commentEditText.getEnterCount()
            view.textNumTextView.text = "${MAX_LENGTH - textNum}字，${MAX_LINE - lineCount}行"
        }

        fun setSendIcon() {
            val isEnable = view.commentEditText.text.isNotBlank()
            view.commentSendButton.isEnabled = isEnable
            view.commentSendButton.setImageDrawable(
                    if (isEnable) sendEnableIcon else sendDisableIcon
            )
        }

        setLengthText()
        view.commentEditText.addTextChangedListener(MaxLinesTextWatcher(view.commentEditText, MAX_LINE))
        view.commentEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {
                setLengthText()
                setSendIcon()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        setSendIcon()
        view.commentSendButton.setOnClickListener {
            onCommentSendListener?.onCommentSend("${getCommentPre()}${view.commentEditText.text.toString().trim()}")
            // 提前关闭，之后关闭可能会有些小问题
            view.commentEditText.hideInput()
        }

        view.commentEditText.post {
            view.commentEditText.showInput()
        }
        view.hintEditText.requestFocus()

        if (isCommentOther) {
            view.infoTextView.text = "评论动态"
        } else {
            view.infoTextView.text = "回复：${getReplyUser()}"
        }
    }

    override fun onStop() {
        activity.hideInput()
        super.onStop()
    }

    override fun onDetach() {
        onCommentSendListener = null
        super.onDetach()
    }

    fun show(fg: FragmentManager) {
        this.show(fg, DIALOG_EDIT_COMMENT)
    }

    interface OnCommentSendListener {
        fun onCommentSend(comment: String)
    }

    private fun getCommentPre() = if (isCommentOther) "" else "@${getReplyUser()}："

    private fun getReplyUser() = otherCommentData?.schoolDymaticComment?.user?.name ?: "其他用户"
}