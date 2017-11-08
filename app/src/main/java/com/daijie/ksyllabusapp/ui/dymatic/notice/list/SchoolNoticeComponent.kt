package com.daijie.ksyllabusapp.ui.dymatic.notice.list

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.user.UserComponent
import dagger.Component

/**
 * Created by daidaijie on 2017/10/22.
 */
@ActivityScoped
@Component(modules = arrayOf(SchoolNoticeModule::class), dependencies = arrayOf(UserComponent::class))
interface SchoolNoticeComponent {

    fun inject(schoolNoticeFragment: SchoolNoticeFragment)
}