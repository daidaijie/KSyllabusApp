package com.daijie.ksyllabusapp.ui.dymatic.circle.list

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.user.UserComponent
import dagger.Component

/**
 * Created by daidaijie on 17-10-22.
 */
@ActivityScoped
@Component(modules = arrayOf(SchoolCircleModule::class), dependencies = arrayOf(UserComponent::class))
interface SchoolCircleComponent {

    fun inject(schoolCircleFragment: SchoolCircleFragment)
}