package com.daijie.ksyllabusapp.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.data.CollectionRecordChoice
import kotlinx.android.synthetic.main.item_collection_record.view.*

/**
 * Created by daidaijie on 2017/10/15.
 */
class CollectionRecordAdapter(val selectable: Boolean = true) :
        BaseQuickAdapter<CollectionRecordChoice, CollectionRecordAdapter.ViewHolder>(R.layout.item_collection_record) {

    private var itemClickListener: ((CollectionRecordChoice) -> Unit)? = null

    fun setItemClickListener(listener: (CollectionRecordChoice) -> Unit) {
        itemClickListener = listener
    }

    override fun convert(helper: ViewHolder, item: CollectionRecordChoice) {
        helper.bindCollectionRecord(item)
    }


    inner class ViewHolder(val mView: View) : BaseViewHolder(mView) {
        fun bindCollectionRecord(recordChoice: CollectionRecordChoice) {
            mView.usernameTextView.text = recordChoice.collectionRecord.account
            mView.recordSelectedCheckBox.isChecked = recordChoice.isSelected
            if (selectable) {
                mView.checkboxLayout.visibility = View.VISIBLE
                with(mView.recordSelectedCheckBox) {
                    this.setOnClickListener {
                        recordChoice.isSelected = isChecked
                    }
                    mView.recordSelectedLayout.setOnClickListener {
                        itemClickListener?.invoke(CollectionRecordChoice(
                                recordChoice.collectionRecord, true
                        ))
                    }
                }
            } else {
                mView.checkboxLayout.visibility = View.GONE
            }
        }
    }

}