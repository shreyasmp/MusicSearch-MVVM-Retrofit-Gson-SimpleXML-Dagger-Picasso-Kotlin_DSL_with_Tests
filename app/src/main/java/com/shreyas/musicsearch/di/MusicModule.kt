package com.shreyas.musicsearch.di

import android.app.Application
import android.content.Context
import com.shreyas.musicsearch.di.modules.*
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

@Module(
    includes = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        ServiceModule::class,
        PicassoModule::class,
        TrackViewModule::class,
        TrackViewModelModule::class
    ]
)
abstract class MusicModule {

    companion object {
        @Provides
        fun provideApplicationContext(application: Application): Context {
            return application.applicationContext
        }
    }
}