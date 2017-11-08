package com.daijie.ksyllabusapp.ui.oa.list

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.AppComponent
import dagger.Component

/**
 * Created by daidaijie on 2017/10/18.
 */
@ActivityScoped
@Component(modules = arrayOf(OAListModule::class), dependencies = arrayOf(AppComponent::class))
interface OAListComponent {

    fun inject(activity: OAListActivity)
}