package com.shreyas.musicsearch.di.modules

import androidx.lifecycle.ViewModel
import com.shreyas.musicsearch.di.annotations.ViewModelKey
import com.shreyas.musicsearch.viewmodel.TrackLyricViewModel
import com.shreyas.musicsearch.viewmodel.TrackSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TrackViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TrackSearchViewModel::class)
    abstract fun bindTrackSearchViewModel(trackSearchViewModel: TrackSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrackLyricViewModel::class)
    abstract fun bindTrackLyricViewModel(trackLyricViewModel: TrackLyricViewModel): ViewModel
}