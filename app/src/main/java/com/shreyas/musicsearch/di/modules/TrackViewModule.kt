package com.shreyas.musicsearch.di.modules

import com.shreyas.musicsearch.di.annotations.ActivityScope
import com.shreyas.musicsearch.view.MainActivity
import com.shreyas.musicsearch.view.TrackLyricFragment
import com.shreyas.musicsearch.view.TrackSearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TrackViewModule {

    //Activities here
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    //Fragments here
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributesTrackSearchFragment(): TrackSearchFragment

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributesTrackLyricFragment(): TrackLyricFragment
}