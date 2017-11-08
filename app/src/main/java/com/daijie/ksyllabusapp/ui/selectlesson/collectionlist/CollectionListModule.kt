package com.daijie.ksyllabusapp.ui.selectlesson.collectionlist

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import dagger.Module
import dagger.Provides

/**
 * Created by daidaijie on 2017/10/15.
 */
@Module
class CollectionListModule(val view: CollectionListContract.View) {

    @ActivityScoped
    @Provides
    fun provideView() = view
}