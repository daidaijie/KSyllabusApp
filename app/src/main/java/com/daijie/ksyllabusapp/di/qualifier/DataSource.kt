package com.daijie.ksyllabusapp.di.qualifier

import javax.inject.Qualifier

/**
 * Created by daidaijie on 17-9-28.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Local

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Remote