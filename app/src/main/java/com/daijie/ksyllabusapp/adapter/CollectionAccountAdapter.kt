package com.daijie.ksyllabusapp.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daijie.ksyllabusapp.R
import kotlinx.android.synthetic.main.item_collection_account.view.*

/**
 * Created by daidaijie on 2017/10/16.
 */
class CollectionAccountAdapter(accounts: List<String>)
    : BaseQuickAdapter<String, CollectionAccountAdapter.ViewHolder>(R.layout.item_collection_account, accounts) {

    override fun convert(helper: ViewHolder, item: String) {
        helper.bindAccount(item)
    }

    inner class ViewHolder(val mView: View) : BaseViewHolder(mView) {
        fun bindAccount(account: String) {
            mView.usernameTextView.text = account
        }
    }
}