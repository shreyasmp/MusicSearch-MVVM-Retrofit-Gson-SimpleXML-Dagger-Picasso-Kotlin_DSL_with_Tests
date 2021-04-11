package com.shreyas.musicsearch.di.modules

import androidx.lifecycle.ViewModelProvider
import com.shreyas.musicsearch.di.DaggerViewModelFactory
import com.shreyas.musicsearch.repository.TrackRepository
import com.shreyas.musicsearch.repository.TrackRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bind(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @Singleton
    abstract fun providesTrackRepository(repositoryImpl: TrackRepositoryImpl): TrackRepository
}