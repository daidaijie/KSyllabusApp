package com.daijie.ksyllabusapp.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.ext.time2char
import kotlinx.android.synthetic.main.item_multi_time.view.*

/**
 * Created by daidaijie on 17-10-18.
 */
class MultiTimeAdapter(times: List<Int>) :
        BaseQuickAdapter<Int, MultiTimeAdapter.ViewHolder>(R.layout.item_multi_time, times) {

    override fun convert(helper: ViewHolder, item: Int) {
        helper.bindTime(item)
    }

    inner class ViewHolder(val mView: View) : BaseViewHolder(mView) {
        fun bindTime(time: Int) {
            mView.timeTextView.text = "第${time2char(time)}节"
        }
    }
}