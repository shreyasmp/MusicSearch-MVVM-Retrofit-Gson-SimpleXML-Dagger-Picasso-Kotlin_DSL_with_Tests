package com.shreyas.musicsearch.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shreyas.musicsearch.base.BaseViewModel
import com.shreyas.musicsearch.model.TrackLyrics
import com.shreyas.musicsearch.repository.TrackRepositoryImpl
import com.shreyas.musicsearch.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TrackLyricViewModel @Inject constructor(private val repository: TrackRepositoryImpl) :
    BaseViewModel() {

    companion object {
        private val TAG = TrackLyricViewModel::class.java.simpleName
    }

    val trackLyric: MutableLiveData<TrackLyrics> = MutableLiveData()

    fun fetchTrackLyric(artistName: String, songName: String) {
        isLoading.value = true
        viewModelScope.launch {
            val result = withContext((Dispatchers.IO)) {
                repository.getTrackLyric(artistName, songName)
            }
            when (result) {
                is ResultWrapper.SUCCESS -> {
                    isLoading.value = false
                    isError.value = false
                    trackLyric.value = result.value.value
                    Log.d(TAG, "Track Lyric: ${result.value.value}")
                }
                is ResultWrapper.FAILURE -> {
                    isLoading.value = false
                    isError.value = true
                    trackLyric.value = null
                }
            }
        }
    }
}