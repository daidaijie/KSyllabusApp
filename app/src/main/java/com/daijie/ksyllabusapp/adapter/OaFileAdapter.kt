package com.daijie.ksyllabusapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseViewHolder
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.data.OaFile
import com.daijie.ksyllabusapp.utils.RecyclerViewState
import kotlinx.android.synthetic.main.item_oa_file.view.*

/**
 * Created by daidaijie on 17-10-21.
 */
class OaFileAdapter(private val context: Context, private val mRecyclerView: RecyclerView)
    : BaseQuickStateAdapter<OaFile, OaFileAdapter.ViewHolder>(R.layout.item_oa_file) {

    private val mLoadingView: View by lazy {
        LayoutInflater.from(context).inflate(
                R.layout.loading_oa_file, mRecyclerView.parent as ViewGroup, false
        )
    }

    private val mEmptyView: View by lazy {
        LayoutInflater.from(context).inflate(
                R.layout.empty_oa_file, mRecyclerView.parent as ViewGroup, false
        )
    }

    private val mErrorView: View by lazy {
        LayoutInflater.from(context).inflate(
                R.layout.error_oa_file, mRecyclerView.parent as ViewGroup, false
        ).apply {
            setOnClickListener {
                onErrorClickListener?.invoke()
            }
        }
    }

    override fun convert(helper: ViewHolder, item: OaFile) {
        helper.bindFile(item)
    }

    override fun showError() {
        super.showError()
        emptyView = mErrorView
    }

    override fun showEmpty() {
        super.showEmpty()
        emptyView = mEmptyView
    }

    override fun showPre() {
        super.showPre()
        emptyView = mLoadingView
    }

    inner class ViewHolder(val mView: View) : BaseViewHolder(mView) {
        fun bindFile(oaFile: OaFile) {
            mView.fileTypeTextView.text = oaFile.docFileType
            mView.fileNameTextView.text = oaFile.imageFileName
        }
    }
}