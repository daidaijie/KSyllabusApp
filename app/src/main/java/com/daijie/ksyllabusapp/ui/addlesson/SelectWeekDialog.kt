package com.daijie.ksyllabusapp.ui.addlesson

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.CheckedTextView
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.SelectWeekAdapter
import com.daijie.ksyllabusapp.base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_select_week.view.*

/**
 * Created by daidaijie on 2017/10/4.
 */

class SelectWeekDialog : BaseDialogFragment() {

    override val contentViewId = R.layout.dialog_select_week

    val checkTextViewSet = mutableSetOf<CheckedTextView>()

    private var onWeekSelected: ((String) -> Unit)? = null

    fun setOnWeekSelectedListener(listener: (weeks: String) -> Unit) {
        onWeekSelected = listener
    }

    companion object {
        const val DIALOG_SELECT_WEEK = "SelectWeekDialog"

        const val EXTRA_WEEKS = "weeks"

        @JvmStatic
        fun newInstance(weeks: String) = SelectWeekDialog().apply {
            val args = Bundle()
            args.putString(EXTRA_WEEKS, weeks)
            arguments = args
        }
    }

    fun show(fg: FragmentManager) {
        this.show(fg, DIALOG_SELECT_WEEK)
    }

    override fun initDialog(view: View, savedInstanceState: Bundle?): Dialog {
        val weekString = arguments.getString(EXTRA_WEEKS, "")
        val weeks = MutableList(16, { index ->
            if (index >= weekString.length) 0 else (weekString[index].toInt() - '0'.toInt())
        })
        val weekAdapter = SelectWeekAdapter(weeks)
        view.weekRecyclerView.layoutManager = GridLayoutManager(activity, 4, GridLayoutManager.VERTICAL, false)
        view.weekRecyclerView.adapter = weekAdapter
        weekAdapter.setOnWeekChangedListener {
            notifyCheck(view, weeks)
        }

        notifyCheck(view, weeks)
        setupCheckTextView(view.singleWeekTextView, weekAdapter, weeks) { index -> if (index % 2 == 0) 1 else 0 }
        setupCheckTextView(view.doubleWeekTextView, weekAdapter, weeks) { index -> if (index % 2 == 1) 1 else 0 }
        setupCheckTextView(view.fullWeekTextView, weekAdapter, weeks) { 1 }

        return AlertDialog.Builder(activity)
                .setTitle("选择周数")
                .setView(view)
                .setPositiveButton("确定", { _, _ ->
                    onWeekSelected?.invoke(weeks.joinToString(""))
                })
                .setNegativeButton("取消", null)
                .create()
    }

    fun notifyCheck(view: View, weeks: MutableList<Int>) {
        var isSingle = true
        var isDouble = true
        var isFull = true
        for ((index, value) in weeks.withIndex()) {
            if (value == 0) {
                isFull = false
            }
            when {
                value == 0 && index % 2 == 0 -> {
                    isSingle = false
                }
                value == 0 && index % 2 == 1 -> {
                    isDouble = false
                }
                value == 1 && index % 2 == 0 -> {
                    isDouble = false
                }
                value == 1 && index % 2 == 1 -> {
                    isSingle = false
                }
            }
        }
        clearWeekTypeCheck()
        if (isSingle) {
            view.singleWeekTextView.isChecked = true
        } else if (isDouble) {
            view.doubleWeekTextView.isChecked = true
        } else if (isFull) {
            view.fullWeekTextView.isChecked = true
        }
    }

    private fun setupCheckTextView(checkedTextView: CheckedTextView, adapter: SelectWeekAdapter,
                                   weeks: MutableList<Int>, mapIndex: (Int) -> Int) {
        checkTextViewSet.add(checkedTextView)
        checkedTextView.setOnClickListener {
            if (checkedTextView.isChecked) {
                clearWeekTypeCheck()
                for ((index, _) in weeks.withIndex()) {
                    weeks[index] = 0
                }
            } else {
                clearWeekTypeCheck()
                checkedTextView.isChecked = true
                for ((index, _) in weeks.withIndex()) {
                    weeks[index] = mapIndex(index)
                }
            }
            adapter.notifyDataSetChanged()
        }
    }

    fun clearWeekTypeCheck() {
        checkTextViewSet.forEach {
            it.isChecked = false
        }
    }
}