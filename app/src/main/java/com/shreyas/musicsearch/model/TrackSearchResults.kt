package com.shreyas.musicsearch.model

import java.io.Serializable

data class TrackSearchResults(
    val resultCount: Int,
    val results: MutableList<TrackDetail>
) : Serializable {

}
