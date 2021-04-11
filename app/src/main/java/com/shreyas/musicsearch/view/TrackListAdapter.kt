package com.shreyas.musicsearch.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.shreyas.musicsearch.R
import com.shreyas.musicsearch.databinding.MusicSearchListItemBinding
import com.shreyas.musicsearch.model.TrackDetail
import com.squareup.picasso.Picasso
import javax.inject.Inject

open class TrackListAdapter @Inject constructor(private val picasso: Picasso) :
    RecyclerView.Adapter<TrackListAdapter.TrackViewHolder>() {

    inner class TrackViewHolder(val binding: MusicSearchListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(trackDetail: TrackDetail) {
            binding.trackDetail = trackDetail
        }
    }

    @VisibleForTesting
    internal lateinit var trackSearchedList: MutableList<TrackDetail>

    internal lateinit var trackListItemClickListener: TrackListItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding: MusicSearchListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.music_search_list_item,
            parent,
            false
        )
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackSearchedList[position])
        holder.binding.apply {
            picasso.load(trackSearchedList[position].artworkUrl100).placeholder(R.drawable.no_photo)
                .into(trackAlbumArt)
            trackDetailCard.setOnClickListener {
                trackListItemClickListener.onClickTrack(trackSearchedList[position])
            }
        }
    }

    override fun getItemCount(): Int = trackSearchedList.size

    fun updateTrackSearchList(trackList: MutableList<TrackDetail>) {
        trackSearchedList = trackList
    }
}