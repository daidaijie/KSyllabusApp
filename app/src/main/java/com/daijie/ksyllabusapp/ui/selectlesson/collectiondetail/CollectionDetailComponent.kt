package com.daijie.ksyllabusapp.ui.selectlesson.collectiondetail

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.user.UserComponent
import dagger.Component

/**
 * Created by daidaijie on 2017/10/15.
 */
@ActivityScoped
@Component(modules = arrayOf(CollectionDetailModule::class), dependencies = arrayOf(UserComponent::class))
interface CollectionDetailComponent {

    fun inject(activity: CollectionDetailActivity)
}