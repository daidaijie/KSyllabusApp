package com.daijie.ksyllabusapp.ui.oa.list

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.view.View
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.base.BaseDialogFragment
import com.daijie.ksyllabusapp.data.OACompany
import com.daijie.ksyllabusapp.ext.moveCursorToEnd
import kotlinx.android.synthetic.main.dialog_search_oa.view.*

/**
 * Created by daidaijie on 2017/10/20.
 */
class SearchOaDialog : BaseDialogFragment() {

    companion object {
        const val DIALOG_SEARCH_OA = "SearchOaDialog"
        const val EXTRA_LAST_POSITION = "lastPosition"
        const val EXTRA_LAST_KEY_WORD = "lastKeyWord"

        @JvmStatic
        fun newInstance(lastKeyWord: String, lastPosition: Int) = SearchOaDialog().apply {
            val args = Bundle()
            args.putString(EXTRA_LAST_KEY_WORD, lastKeyWord)
            args.putInt(EXTRA_LAST_POSITION, lastPosition)
            arguments = args
        }
    }

    private val mLastPosition: Int  by lazy {
        arguments.getInt(EXTRA_LAST_POSITION)
    }

    private val mLastKeyWord: String by lazy {
        arguments.getString(EXTRA_LAST_KEY_WORD)
    }

    private var onSearchButtonClickListener: ((Int, String) -> Unit)? = null

    override val contentViewId = R.layout.dialog_search_oa

    override fun initDialog(view: View, savedInstanceState: Bundle?): Dialog {
        view.companyTextInputSpinner.setItems(OACompany.oaNames)
        view.companyTextInputSpinner.setSelection(mLastPosition)
        view.keywordEditText.setText(mLastKeyWord)
        view.keywordEditText.moveCursorToEnd()

        return AlertDialog.Builder(activity)
                .setTitle("搜索")
                .setView(view)
                .setPositiveButton("搜索", { _, _ ->
                    if (activity != null) {
                        onSearchButtonClickListener?.invoke(
                                view.companyTextInputSpinner.selectedItemPosition,
                                view.keywordEditText.text.toString()
                        )
                    }
                })
                .create()
    }

    fun show(fg: FragmentManager) {
        show(fg, DIALOG_SEARCH_OA)
    }

    fun setonSearchButtonClickListener(listener: (position: Int, keyword: String) -> Unit) {
        onSearchButtonClickListener = listener
    }
}