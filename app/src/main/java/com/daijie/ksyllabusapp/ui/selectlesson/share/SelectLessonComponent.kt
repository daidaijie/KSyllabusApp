package com.daijie.ksyllabusapp.ui.selectlesson.share

import cn.edu.stu.syllabus.di.scope.FragmentScoped
import com.daijie.ksyllabusapp.user.UserComponent
import dagger.Component

/**
 * Created by daidaijie on 2017/10/6.
 */

@FragmentScoped
@Component(modules = arrayOf(SelectLessonModule::class), dependencies = arrayOf(UserComponent::class))
interface SelectLessonComponent {
    fun inject(activity: ShareLessonActivity)
}