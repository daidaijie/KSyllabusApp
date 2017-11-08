package com.daijie.ksyllabusapp.ui.selectlesson.collectiondetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.CollectionRecordAdapter
import com.daijie.ksyllabusapp.ui.addlesson.SelectWeekDialog
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.base.ToLoginViewImpl
import com.daijie.ksyllabusapp.data.CollectionRecordChoice
import com.daijie.ksyllabusapp.ext.getDrawableById
import com.daijie.ksyllabusapp.ext.parseWeekDay
import com.daijie.ksyllabusapp.ext.toastE
import com.daijie.ksyllabusapp.ext.toastS
import com.daijie.ksyllabusapp.ui.selectlesson.collectionsyllabus.CollectionSyllabusActivity
import com.daijie.ksyllabusapp.widgets.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_collection_detail.*
import kotlinx.android.synthetic.main.header_collection_record.*
import kotlinx.android.synthetic.main.view_hint_edit_text.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/15.
 */
class CollectionDetailActivity : BaseActivity(), CollectionDetailContract.View {
    companion object {
        const val EXTRA_CODE = "code"

        @JvmStatic
        fun newIntent(context: Context, code: String)
                = Intent(context, CollectionDetailActivity::class.java).apply {
            putExtra(EXTRA_CODE, code)
        }
    }

    private val code: String by lazy {
        intent.getStringExtra(EXTRA_CODE)
    }

    private var weeks: String = "1111111111111111"

    private val toLoginView = ToLoginViewImpl(this)

    private val collectionRecordAdapter = CollectionRecordAdapter()

    @Inject
    lateinit var collectionDetailPresenter: CollectionDetailPresenter

    override var titleToolbar: Toolbar? = null
        get() = appToolbar

    override var titleText: TextView? = null
        get() = titleTextView

    override val contentViewId = R.layout.activity_collection_detail

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("课表收集详情")
        initMenu()
        initWeekEditLayout()
        initRecyclerView()
        initInject()
        fullWeekRadioButton.isChecked = true
        hintEditText.requestFocus()
        collectionDetailPresenter.loadCollectionDetail(code)
    }

    private fun initMenu() {
        appToolbar.inflateMenu(R.menu.menu_finish)
        appToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_finish) {
                collectionDetailPresenter.handleCollectionChoice(
                        collectionRecordAdapter.data, weeks, fullWeekRadioButton.isChecked
                )
            }
            true
        }
    }

    override fun onDestroy() {
        collectionDetailPresenter.unsubscribe()
        super.onDestroy()
    }

    private fun initInject() {
        DaggerCollectionDetailComponent.builder()
                .userComponent(App.userComponent)
                .collectionDetailModule(CollectionDetailModule(this))
                .build().inject(this)
    }

    private fun initWeekEditLayout() {
        weekEditLayout.editText.setText(parseWeekDay(weeks = weeks))
        weekEditLayout.setOnEditerClickListener {
            val dialog = SelectWeekDialog.newInstance(weeks)
            dialog.setOnWeekSelectedListener { weeks ->
                this@CollectionDetailActivity.weeks = weeks
                weekEditLayout.editText.setText(parseWeekDay(weeks = weeks))
            }
            dialog.show(supportFragmentManager)
        }
    }

    private fun initRecyclerView() {
        lessonChoiceRecyclerView.layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST,
                getDrawableById(R.drawable.dev_line_padding16)
        )
        itemDecoration.setHeight(1)
        lessonChoiceRecyclerView.addItemDecoration(itemDecoration)
        lessonChoiceRecyclerView.adapter = collectionRecordAdapter
        collectionRecordAdapter.setItemClickListener { collectionRecordChoice ->
            collectionDetailPresenter.handleCollectionChoice(
                    listOf(collectionRecordChoice), weeks, fullWeekRadioButton.isChecked
            )
        }
    }

    override fun showCollectionRecords(records: List<CollectionRecordChoice>) {
        collectionRecordAdapter.setNewData(records)
    }

    override fun showSuccess(msg: String) {
        toastS(msg)
    }

    override fun showError(msg: String) {
        toastE(msg)
    }

    override fun toSyllabusAct(syllabusJson: String) {
        startActivity(CollectionSyllabusActivity.newIntent(this, syllabusJson))
    }

    override fun toLoginAct() {
        toLoginView.toLoginAct()
    }
}