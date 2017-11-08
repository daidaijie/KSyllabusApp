package com.daijie.ksyllabusapp.ui.selectlesson.collectiondetail

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import dagger.Module
import dagger.Provides

/**
 * Created by daidaijie on 2017/10/15.
 */
@Module
class CollectionDetailModule(val view: CollectionDetailContract.View) {

    @Provides
    @ActivityScoped
    fun provideView() = view
}