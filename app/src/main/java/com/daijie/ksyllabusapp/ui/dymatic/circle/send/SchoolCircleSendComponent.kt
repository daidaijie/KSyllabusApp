package com.daijie.ksyllabusapp.ui.dymatic.circle.send

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.user.UserComponent
import dagger.Component

/**
 * Created by daidaijie on 17-10-29.
 */
@ActivityScoped
@Component(modules = arrayOf(SchoolCircleSendModule::class), dependencies = arrayOf(UserComponent::class))
interface SchoolCircleSendComponent {

    fun inject(schoolCircleSendActivity: SchoolCircleSendActivity)

}