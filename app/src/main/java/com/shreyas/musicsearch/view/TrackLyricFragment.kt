package com.shreyas.musicsearch.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import com.shreyas.musicsearch.R
import com.shreyas.musicsearch.base.BaseFragment
import com.shreyas.musicsearch.databinding.FragmentMusicLyricBinding
import com.shreyas.musicsearch.model.TrackDetail
import com.shreyas.musicsearch.viewmodel.TrackLyricViewModel

class TrackLyricFragment : BaseFragment<TrackLyricViewModel>() {

    companion object {
        private val TAG = TrackLyricFragment::class.java.simpleName
    }

    @VisibleForTesting
    internal lateinit var binding: FragmentMusicLyricBinding

    private lateinit var trackDetail: TrackDetail

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_music_lyric,
            container,
            false
        )
        binding.apply {
            lifecycleOwner = this@TrackLyricFragment
            viewModel = this@TrackLyricFragment.viewModel
        }

        trackDetail = arguments?.getSerializable("Selected_Track") as TrackDetail

        Log.d(TAG, "Received Track Details: $trackDetail")

        viewModel.fetchTrackLyric(trackDetail.artistName, trackDetail.trackName)

        return binding.root
    }

    override fun getTitle(): String = getString(R.string.music_lyric)
}