package com.daijie.ksyllabusapp.ui.selectlesson.collectionsyllabus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.CollectionAccountAdapter
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.ext.getDrawableById
import com.daijie.ksyllabusapp.widgets.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_collection_accounts.*
import kotlinx.android.synthetic.main.view_toolbar.*

/**
 * Created by daidaijie on 17-10-16.
 */
class CollectionAccountsActivity : BaseActivity() {

    companion object {
        const val EXTRA_ACCOUNTS = "accounts"
        fun newIntent(context: Context, accounts: List<String>) = Intent(context, CollectionAccountsActivity::class.java)
                .apply {
                    putExtra(EXTRA_ACCOUNTS, accounts.toTypedArray())
                }
    }

    val accounts: List<String> by lazy {
        intent.getStringArrayExtra(EXTRA_ACCOUNTS).toList()
    }

    override var titleToolbar: Toolbar? = null
        get() = appToolbar

    override var titleText: TextView? = null
        get() = titleTextView

    override val contentViewId = R.layout.activity_collection_accounts

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("该课时的用户账号")
        accountsRecyclerView.layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST,
                getDrawableById(R.drawable.dev_line_full)
        )
        itemDecoration.setHeight(1)
        accountsRecyclerView.addItemDecoration(itemDecoration)
        accountsRecyclerView.adapter = CollectionAccountAdapter(accounts)
    }
}