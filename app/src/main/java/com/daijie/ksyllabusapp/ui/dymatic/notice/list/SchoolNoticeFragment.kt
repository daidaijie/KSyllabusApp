package com.daijie.ksyllabusapp.ui.dymatic.notice.list

import android.support.v4.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.adapter.SchoolDymaticAdapter
import com.daijie.ksyllabusapp.adapter.SchoolNoticeAdapter
import com.daijie.ksyllabusapp.ui.dymatic.base.list.SchoolDymaticContract
import com.daijie.ksyllabusapp.ui.dymatic.base.list.SchoolDymaticFragment
import com.daijie.ksyllabusapp.ui.dymatic.base.module.SchoolDymaticDeleteModule
import com.daijie.ksyllabusapp.ui.dymatic.base.module.SchoolDymaticLikeModule
import com.daijie.ksyllabusapp.ui.dymatic.notice.detail.SchoolNoticeDetailActivity
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/22.
 */
class SchoolNoticeFragment : SchoolDymaticFragment(), SchoolNoticeContract.View
        , SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    companion object {
        @JvmStatic
        fun newInstance() = SchoolNoticeFragment()
    }

    override val schoolDymaticPresenter: SchoolDymaticContract.Presenter
        get() = schoolNoticePresenter

    override val schoolDymaticAdapter: SchoolDymaticAdapter<*>
        get() = schoolNoticeAdapter

    override val detailActivityClass: Class<SchoolNoticeDetailActivity>
        get() = SchoolNoticeDetailActivity::class.java

    @Inject
    lateinit var schoolNoticePresenter: SchoolNoticePresenter

    private val schoolNoticeAdapter: SchoolNoticeAdapter by lazy {
        SchoolNoticeAdapter(activity)
    }

    override fun initInject() {
        DaggerSchoolNoticeComponent.builder()
                .userComponent(App.userComponent)
                .schoolDymaticDeleteModule(SchoolDymaticDeleteModule(schoolDymaticDeleteView))
                .schoolDymaticLikeModule(SchoolDymaticLikeModule(schoolDymaticLikeView))
                .schoolNoticeModule(SchoolNoticeModule(this))
                .build().inject(this)
    }

    override fun letPresenterLoadData() {
        schoolNoticePresenter.loadDatas(schoolNoticeAdapter.currentPage, SchoolDymaticAdapter.PAGE_LIMIT)
    }
}