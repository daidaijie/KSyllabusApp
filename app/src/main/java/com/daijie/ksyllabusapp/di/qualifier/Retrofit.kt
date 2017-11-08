package com.daijie.ksyllabusapp.di.qualifier

import javax.inject.Qualifier

/**
 * Created by daidaijie on 17-9-28.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SchoolRetrofitQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LCRetrofitQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LCImageRetrofitQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class OARetrofitQualifier