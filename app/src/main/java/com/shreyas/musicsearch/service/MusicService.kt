package com.shreyas.musicsearch.service

import com.shreyas.musicsearch.model.TrackSearchResults
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicService {

    @GET("search/")
    fun fetchTrackSearchListAsync(@Query("term") searchWord: String): Deferred<Response<TrackSearchResults>>
}