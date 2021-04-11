package com.shreyas.musicsearch.view

import com.shreyas.musicsearch.model.TrackDetail

interface TrackListItemClickListener {
    fun onClickTrack(trackDetail: TrackDetail)
}