package com.shreyas.musicsearch.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shreyas.musicsearch.R
import com.shreyas.musicsearch.base.BaseFragment
import com.shreyas.musicsearch.databinding.FragmentMusicSearchListBinding
import com.shreyas.musicsearch.model.TrackDetail
import com.shreyas.musicsearch.viewmodel.TrackSearchViewModel
import javax.inject.Inject

class TrackSearchFragment : BaseFragment<TrackSearchViewModel>(), TrackListItemClickListener {

    companion object {
        private val TAG = TrackSearchFragment::class.java.simpleName
    }

    @VisibleForTesting
    internal lateinit var binding: FragmentMusicSearchListBinding

    @Inject
    lateinit var trackAdapter: TrackListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_music_search_list,
            container,
            false
        )
        binding.apply {
            lifecycleOwner = this@TrackSearchFragment
            viewModel = this@TrackSearchFragment.viewModel
        }

        subscribeUI()
        bindView()

        return binding.root
    }

    override fun getTitle(): String = getString(R.string.music_search)

    private fun bindView() {
        binding.musicSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d(TAG, " Entered music search word: $query")
                viewModel.fetchTrackSearchList(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun subscribeUI() {
        viewModel.trackSearchList.observe(viewLifecycleOwner, { trackSearchList ->
            binding.musicSearchList.layoutManager = LinearLayoutManager(context)
            binding.musicSearchList.adapter = trackAdapter
            trackAdapter.trackListItemClickListener = this@TrackSearchFragment
            trackAdapter.updateTrackSearchList(trackSearchList)
        })
    }

    override fun onClickTrack(trackDetail: TrackDetail) {
        val bundle = Bundle()
        bundle.putSerializable("Selected_Track", trackDetail)
        findNavController().navigate(R.id.action_TrackSearchFragment_to_TrackLyricFragment, bundle)
    }
}