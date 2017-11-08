package com.daijie.ksyllabusapp.ui.dymatic.notice.detail

import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.adapter.SchoolDymaticAdapter
import com.daijie.ksyllabusapp.adapter.SchoolNoticeAdapter
import com.daijie.ksyllabusapp.ui.dymatic.base.detail.SchoolDymaticCommentContract
import com.daijie.ksyllabusapp.ui.dymatic.base.detail.SchoolDymaticDetailActivity
import com.daijie.ksyllabusapp.ui.dymatic.base.module.SchoolDymaticDeleteModule
import com.daijie.ksyllabusapp.ui.dymatic.base.module.SchoolDymaticLikeModule
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/24.
 */
class SchoolNoticeDetailActivity : SchoolDymaticDetailActivity(), SchoolNoticeCommentContract.View {

    @Inject
    lateinit var schoolNoticeCommentPresenter: SchoolNoticeCommentPresenter

    override val schoolDymaticCommentPresenter: SchoolDymaticCommentContract.Presenter
        get() = schoolNoticeCommentPresenter

    override val schoolDymaticAdapter: SchoolDymaticAdapter<*>
        get() = schoolNoticeAdapter

    private val schoolNoticeAdapter: SchoolNoticeAdapter by lazy {
        SchoolNoticeAdapter(this)
    }

    override fun initInject() {
        DaggerSchoolNoticeCommentComponent.builder()
                .userComponent(App.userComponent)
                .schoolDymaticDeleteModule(SchoolDymaticDeleteModule(schoolDymaticDeleteViewProxy))
                .schoolDymaticLikeModule(SchoolDymaticLikeModule(schoolDymaticLikeView))
                .schoolNoticeCommentModule(SchoolNoticeCommentModule(this))
                .build().inject(this)
    }

}