package com.shreyas.musicsearch.viewmodel

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shreyas.musicsearch.base.BaseViewModel
import com.shreyas.musicsearch.model.TrackDetail
import com.shreyas.musicsearch.repository.TrackRepositoryImpl
import com.shreyas.musicsearch.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TrackSearchViewModel @Inject constructor(private val repository: TrackRepositoryImpl) :
    BaseViewModel() {

    companion object {
        private val TAG = TrackSearchViewModel::class.java.simpleName
    }

    @VisibleForTesting
    internal val _trackSearchList: MutableLiveData<MutableList<TrackDetail>> = MutableLiveData()
    val trackSearchList: LiveData<MutableList<TrackDetail>> = _trackSearchList

    fun fetchTrackSearchList(searchWord: String) {
        isLoading.value = true
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getTrackSearchList(searchWord)
            }
            when (result) {
                is ResultWrapper.SUCCESS -> {
                    isLoading.value = false
                    val trackList = result.value.value?.results
                    if (trackList != null && trackList.isNotEmpty()) {
                        isError.value = false
                        Log.d(TAG, "Track Search List: $trackList")
                        _trackSearchList.value = trackList
                    } else {
                        isError.value = true
                    }
                }
                is ResultWrapper.FAILURE -> {
                    isLoading.value = false
                    isError.value = true
                    _trackSearchList.value = null
                }
            }
        }
    }
}