package com.daijie.ksyllabusapp.ui.dymatic.circle.detail

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.user.UserComponent
import dagger.Component

/**
 * Created by daidaijie on 17-10-25.
 */
@ActivityScoped
@Component(modules = arrayOf(SchoolCircleCommentModule::class), dependencies = arrayOf(UserComponent::class))
interface SchoolCircleCommentComponent {

    fun inject(activity: SchoolCircleDetailActivity)
}