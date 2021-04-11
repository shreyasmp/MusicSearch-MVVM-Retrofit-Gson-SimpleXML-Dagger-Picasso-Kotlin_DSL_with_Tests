package com.shreyas.musicsearch.service

import com.shreyas.musicsearch.model.TrackLyrics
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface LyricService {

    @Headers("Content-Type: text/xml")
    @GET("SearchLyricDirect")
    fun fetchTrackLyricAsync(
        @Query("artist") artistName: String,
        @Query("song") songName: String
    ): Deferred<Response<TrackLyrics>>
}