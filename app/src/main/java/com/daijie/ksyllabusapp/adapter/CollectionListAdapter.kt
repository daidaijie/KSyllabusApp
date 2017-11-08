package com.daijie.ksyllabusapp.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.data.CollectionInfo
import com.daijie.ksyllabusapp.ui.selectlesson.collectiondetail.CollectionDetailActivity
import kotlinx.android.synthetic.main.item_collection_lesson.view.*

/**
 * Created by daidaijie on 2017/10/15.
 */
class CollectionListAdapter : BaseQuickAdapter<CollectionInfo, CollectionListAdapter.ViewHolder>(R.layout.item_collection_lesson) {

    override fun convert(helper: ViewHolder, item: CollectionInfo) {
        helper.bindCollection(item)
    }

    inner class ViewHolder(private val mView: View) : BaseViewHolder(mView) {

        fun bindCollection(info: CollectionInfo) {
            mView.collectionIdTextView.text = info.collectionId
            mView.collectionLayout.setOnClickListener {
                mView.context.startActivity(CollectionDetailActivity.newIntent(mView.context, info.collectionId))
            }
        }
    }
}