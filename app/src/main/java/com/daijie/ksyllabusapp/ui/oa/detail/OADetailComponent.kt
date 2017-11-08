package com.daijie.ksyllabusapp.ui.oa.detail

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.AppComponent
import dagger.Component

/**
 * Created by daidaijie on 17-10-21.
 */
@ActivityScoped
@Component(modules = arrayOf(OADetailModule::class), dependencies = arrayOf(AppComponent::class))
interface OADetailComponent {

    fun inject(oaDetailActivity: OADetailActivity)
}