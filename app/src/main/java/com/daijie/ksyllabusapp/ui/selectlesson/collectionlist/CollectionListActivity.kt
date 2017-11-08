package com.daijie.ksyllabusapp.ui.selectlesson.collectionlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.CollectionListAdapter
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.base.LoadingViewImpl
import com.daijie.ksyllabusapp.base.ToLoginViewImpl
import com.daijie.ksyllabusapp.data.CollectionInfo
import com.daijie.ksyllabusapp.ext.getDrawableById
import com.daijie.ksyllabusapp.ext.toastE
import com.daijie.ksyllabusapp.ext.toastS
import com.daijie.ksyllabusapp.widgets.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_collection_list.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/15.
 */
class CollectionListActivity : BaseActivity(), CollectionListContract.View {

    companion object {
        fun newIntent(context: Context) = Intent(context, CollectionListActivity::class.java)
    }

    @Inject
    lateinit var collectionListPresenter: CollectionListPresenter

    private val collectionListAdpater = CollectionListAdapter()

    override val contentViewId = R.layout.activity_collection_list

    override var titleToolbar: Toolbar? = null
        get() = appToolbar

    override var titleText: TextView? = null
        get() = titleTextView

    private val loadingDialog = LoadingViewImpl(this)

    private val toLoginView = ToLoginViewImpl(this)

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("收集课表")
        initRecyclerView()
        initFab()
        initInject()
        collectionListPresenter.subscribe()
    }

    private fun initFab() {
        applyCollectionFab.setOnClickListener {
            collectionListPresenter.addCollection()
        }
    }

    private fun initRecyclerView() {
        collectionRecyclerView.layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST,
                getDrawableById(R.drawable.dev_line_full)
        )
        itemDecoration.setHeight(1)
        collectionRecyclerView.addItemDecoration(itemDecoration)
        collectionRecyclerView.adapter = collectionListAdpater
    }

    override fun onDestroy() {
        collectionListPresenter.unsubscribe()
        super.onDestroy()
    }

    private fun initInject() {
        DaggerCollectionListComponent.builder()
                .userComponent(App.userComponent)
                .collectionListModule(CollectionListModule(this))
                .build().inject(this)
    }

    override fun showLoadingDialog(msg: String?) {
        loadingDialog.showLoadingDialog(msg)
    }

    override fun hideLoadingDialog() {
        loadingDialog.hideLoadingDialog()
    }

    override fun showCollectionList(collectionList: List<CollectionInfo>) {
        collectionListAdpater.setNewData(collectionList)
    }

    override fun addCollection(collectionInfo: CollectionInfo) {
        collectionListAdpater.addData(0, collectionInfo)
    }

    override fun showSuccess(msg: String) {
        toastS(msg)
    }

    override fun showError(msg: String) {
        toastE(msg)
    }

    override fun toLoginAct() {
        toLoginView.toLoginAct()
    }

}