package com.daijie.ksyllabusapp.ui.dymatic.circle.detail

import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.adapter.SchoolCircleAdapter
import com.daijie.ksyllabusapp.adapter.SchoolDymaticAdapter
import com.daijie.ksyllabusapp.ui.dymatic.base.detail.SchoolDymaticCommentContract
import com.daijie.ksyllabusapp.ui.dymatic.base.detail.SchoolDymaticDetailActivity
import com.daijie.ksyllabusapp.ui.dymatic.base.module.SchoolDymaticDeleteModule
import com.daijie.ksyllabusapp.ui.dymatic.base.module.SchoolDymaticLikeModule
import javax.inject.Inject

/**
 * Created by daidaijie on 2017/10/24.
 */
class SchoolCircleDetailActivity : SchoolDymaticDetailActivity(), SchoolCircleCommentContract.View {

    @Inject
    lateinit var schoolCircleCommentPresenter: SchoolCircleCommentPresenter

    override val schoolDymaticCommentPresenter: SchoolDymaticCommentContract.Presenter
        get() = schoolCircleCommentPresenter

    override val schoolDymaticAdapter: SchoolDymaticAdapter<*>
        get() = schoolCircleAdapter

    private val schoolCircleAdapter: SchoolCircleAdapter by lazy {
        SchoolCircleAdapter(this)
    }

    override fun initInject() {
        DaggerSchoolCircleCommentComponent.builder()
                .userComponent(App.userComponent)
                .schoolDymaticDeleteModule(SchoolDymaticDeleteModule(schoolDymaticDeleteViewProxy))
                .schoolDymaticLikeModule(SchoolDymaticLikeModule(schoolDymaticLikeView))
                .schoolCircleCommentModule(SchoolCircleCommentModule(this))
                .build().inject(this)
    }

}