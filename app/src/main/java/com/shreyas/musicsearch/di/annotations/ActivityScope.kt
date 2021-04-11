package com.shreyas.musicsearch.di.annotations

import javax.inject.Scope

/**
 *  Activity Scope to make it singleton
 */
@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ActivityScope()
