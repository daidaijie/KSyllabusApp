package com.daijie.ksyllabusapp.ui.dymatic.circle.send

import cn.edu.stu.syllabus.di.scope.ActivityScoped
import com.daijie.ksyllabusapp.littlevp.uploadimage.UploadImageModule
import com.daijie.ksyllabusapp.repository.schoolcircle.SchoolCircleActivityModule
import dagger.Module
import dagger.Provides

/**
 * Created by daidaijie on 17-10-29.
 */
@Module(includes = arrayOf(UploadImageModule::class, SchoolCircleActivityModule::class))
class SchoolCircleSendModule(val view: SchoolCircleSendContract.View) {

    @Provides
    @ActivityScoped
    fun provideView() = view
}