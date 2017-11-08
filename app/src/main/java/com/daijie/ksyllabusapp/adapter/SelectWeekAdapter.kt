package com.daijie.ksyllabusapp.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daijie.ksyllabusapp.R
import kotlinx.android.synthetic.main.item_week_select.view.*

/**
 * Created by daidaijie on 2017/10/4.
 */
class SelectWeekAdapter(val weeks: MutableList<Int>)
    : BaseQuickAdapter<Int, SelectWeekAdapter.ViewHolder>(R.layout.item_week_select, weeks) {


    override fun createBaseViewHolder(view: View?): ViewHolder {
        return ViewHolder(view!!)
    }


    override fun convert(helper: SelectWeekAdapter.ViewHolder, item: Int?) {
        item?.let {
            helper.bindWeek(helper.adapterPosition, it)
        }
    }

    private var onWeekChangedListener: (() -> Unit)? = null

    fun setOnWeekChangedListener(listener: () -> Unit) {
        onWeekChangedListener = listener
    }

    inner class ViewHolder(val mView: View) : BaseViewHolder(mView) {

        fun bindWeek(index: Int, isSelected: Int) {
            mView.weekTextView.text = (index + 1).toString()
            setSelected(isSelected)
            mView.weekTextView.setOnClickListener {
                if (weeks[index] == 1) {
                    weeks[index] = 0
                } else {
                    weeks[index] = 1
                }
                setSelected(weeks[index])
                onWeekChangedListener?.invoke()
            }
        }

        fun setSelected(isSelected: Int) {
            mView.weekTextView.isChecked = (isSelected >= 1)
        }
    }
}