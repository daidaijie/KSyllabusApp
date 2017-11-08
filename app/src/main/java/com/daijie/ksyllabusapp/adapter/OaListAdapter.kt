package com.daijie.ksyllabusapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.data.OACompany
import com.daijie.ksyllabusapp.data.OaInfo
import com.daijie.ksyllabusapp.ext.getColorById
import com.daijie.ksyllabusapp.ext.getDrawableById
import com.daijie.ksyllabusapp.utils.RecyclerViewState
import com.daijie.ksyllabusapp.utils.TimeFormatUtils
import com.daijie.ksyllabusapp.widgets.CustomLoadMoreView
import kotlinx.android.synthetic.main.item_oa.view.*
import java.text.ParseException

/**
 * Created by daidaijie on 17-10-19.
 */
class OaListAdapter(val context: Context,
                    val showRead: Boolean)
    : BaseQuickStateAdapter<OaInfo, OaListAdapter.ViewHolder>(R.layout.item_oa) {

    companion object {
        const val PAGE_LIMIT = 10
    }

    private val mEmptyView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.empty_view, recyclerView as ViewGroup, false)
                .apply {
                    val emptyTextView = findViewById<TextView>(R.id.emptyTextView);
                    emptyTextView.text = "查找不到搜索对应的结果"
                }
    }

    private val mErrorView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.error_view, recyclerView as ViewGroup, false)
                .apply {
                    val errorTextView = findViewById<TextView>(R.id.errTextView);
                    errorTextView.text = "查找不到搜索对应的结果"
                    setOnClickListener {
                        onErrorClickListener?.invoke()
                    }
                }
    }


    init {
        setLoadMoreView(CustomLoadMoreView())
    }

    override fun convert(helper: ViewHolder, item: OaInfo) {
        helper.bindOAInfo(item)
    }


    override fun showError() {
        super.showError()
        emptyView = mErrorView
    }

    override fun showEmpty() {
        super.showEmpty()
        emptyView = mEmptyView
    }

    inner class ViewHolder(val mView: View) : BaseViewHolder(mView) {
        fun bindOAInfo(oaInfo: OaInfo) {
            val oaCompany = OACompany.oaCompaniesMap[oaInfo.subCompanyId]
            val icon = getDrawableById(R.drawable.bg_circle)

            if (oaCompany != null) {
                mView.subCompanyNameTextView.text = oaCompany.name
                mView.subCompanyShortNameTextView.text = oaCompany.shortName
            } else {
                mView.subCompanyNameTextView.text = oaInfo.subCompanyName
                mView.subCompanyShortNameTextView.text = oaInfo.subCompanyName.substring(0, 2)
            }
            mView.subCompanyShortNameTextView.background = icon

            mView.titleTextView.text = oaInfo.docSubject
            mView.titleTextView.setTextColor(getColorById(R.color.colorTextPrimary))

            val timeString = "${oaInfo.docValidDate} ${oaInfo.docValidTime}"
            try {
                mView.timeTextView.text = TimeFormatUtils.formatTime(timeString, "yyyy-MM-dd HH:mm:ss")
            } catch (e: ParseException) {
                mView.timeTextView.text = timeString
            }
        }
    }

}