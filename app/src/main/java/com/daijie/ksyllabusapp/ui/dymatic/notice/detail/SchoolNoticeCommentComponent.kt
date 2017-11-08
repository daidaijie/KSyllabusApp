package com.daijie.ksyllabusapp.ui.dymatic.notice.detail

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.user.UserComponent
import dagger.Component

/**
 * Created by daidaijie on 17-10-25.
 */
@ActivityScoped
@Component(modules = arrayOf(SchoolNoticeCommentModule::class), dependencies = arrayOf(UserComponent::class))
interface SchoolNoticeCommentComponent {

    fun inject(activity: SchoolNoticeDetailActivity)
}