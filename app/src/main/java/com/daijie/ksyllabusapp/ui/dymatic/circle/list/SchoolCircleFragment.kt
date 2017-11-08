package com.daijie.ksyllabusapp.ui.dymatic.circle.list

import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.adapter.SchoolCircleAdapter
import com.daijie.ksyllabusapp.adapter.SchoolDymaticAdapter
import com.daijie.ksyllabusapp.ui.dymatic.base.list.SchoolDymaticContract
import com.daijie.ksyllabusapp.ui.dymatic.base.list.SchoolDymaticFragment
import com.daijie.ksyllabusapp.ui.dymatic.base.module.SchoolDymaticDeleteModule
import com.daijie.ksyllabusapp.ui.dymatic.base.module.SchoolDymaticLikeModule
import com.daijie.ksyllabusapp.ui.dymatic.circle.detail.SchoolCircleDetailActivity
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/22.
 */
class SchoolCircleFragment : SchoolDymaticFragment(), SchoolCircleContract.View {

    companion object {
        @JvmStatic
        fun newInstance() = SchoolCircleFragment()
    }

    @Inject
    lateinit var schoolCirclePresenter: SchoolCirclePresenter

    private val schoolCircleAdapter: SchoolCircleAdapter by lazy {
        SchoolCircleAdapter(activity)
    }

    override val schoolDymaticPresenter: SchoolDymaticContract.Presenter
        get() = schoolCirclePresenter

    override val schoolDymaticAdapter: SchoolDymaticAdapter<*>
        get() = schoolCircleAdapter

    override val detailActivityClass: Class<SchoolCircleDetailActivity>
        get() = SchoolCircleDetailActivity::class.java

    override fun initInject() {
        DaggerSchoolCircleComponent.builder()
                .userComponent(App.userComponent)
                .schoolDymaticDeleteModule(SchoolDymaticDeleteModule(schoolDymaticDeleteView))
                .schoolDymaticLikeModule(SchoolDymaticLikeModule(schoolDymaticLikeView))
                .schoolCircleModule(SchoolCircleModule(this))
                .build().inject(this)
    }

    override fun letPresenterLoadData() {
        val beforeId: Int = if (schoolCircleAdapter.currentPage == 0 || schoolCircleAdapter.data.isEmpty()) Integer.MAX_VALUE else
            schoolCircleAdapter.data[schoolCircleAdapter.data.size - 1].schoolNotice.id
        schoolCirclePresenter.loadDatas(beforeId, SchoolDymaticAdapter.PAGE_LIMIT, schoolCircleAdapter.currentPage)
    }
}