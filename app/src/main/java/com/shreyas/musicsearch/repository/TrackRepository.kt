package com.shreyas.musicsearch.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shreyas.musicsearch.model.TrackLyrics
import com.shreyas.musicsearch.model.TrackSearchResults
import com.shreyas.musicsearch.service.LyricService
import com.shreyas.musicsearch.service.MusicService
import com.shreyas.musicsearch.utils.ResultWrapper
import javax.inject.Inject
import javax.inject.Singleton

interface TrackRepository {
    suspend fun getTrackSearchList(searchWord: String): ResultWrapper<LiveData<TrackSearchResults>>

    suspend fun getTrackLyric(
        artistName: String,
        songName: String
    ): ResultWrapper<LiveData<TrackLyrics>>
}

@Singleton
open class TrackRepositoryImpl @Inject constructor(
    private val musicService: MusicService,
    private val lyricService: LyricService
) :
    TrackRepository {

    companion object {
        private val TAG = TrackRepositoryImpl::class.java.simpleName
    }

    override suspend fun getTrackSearchList(searchWord: String): ResultWrapper<LiveData<TrackSearchResults>> {
        val trackSearchResults = MutableLiveData<TrackSearchResults>()
        return try {
            val deferredResponse = musicService.fetchTrackSearchListAsync(searchWord)
            val responseBody = deferredResponse.await()
            if (deferredResponse.isCompleted && responseBody.body() != null) {
                trackSearchResults.postValue(responseBody.body())
                ResultWrapper.SUCCESS(trackSearchResults)
            } else {
                ResultWrapper.FAILURE(null)
            }
        } catch (exception: Exception) {
            Log.d(TAG, "ExceptionL: ${exception.message}")
            ResultWrapper.FAILURE(null)
        }
    }

    override suspend fun getTrackLyric(
        artistName: String,
        songName: String
    ): ResultWrapper<LiveData<TrackLyrics>> {
        val trackLyric = MutableLiveData<TrackLyrics>()
        return try {
            val deferredResponse =
                lyricService.fetchTrackLyricAsync(artistName = artistName, songName = songName)
            val responseBody = deferredResponse.await()
            if (deferredResponse.isCompleted && responseBody.body() != null) {
                trackLyric.postValue(responseBody.body())
                ResultWrapper.SUCCESS(trackLyric)
            } else {
                ResultWrapper.FAILURE(null)
            }
        } catch (exception: Exception) {
            Log.d(TAG, "Exception: ${exception.message}")
            ResultWrapper.FAILURE(null)
        }
    }
}

