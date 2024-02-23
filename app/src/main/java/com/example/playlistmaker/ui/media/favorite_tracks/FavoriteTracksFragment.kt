package com.example.playlistmaker.ui.media.favorite_tracks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.FragmentFavoriteTracksBinding
import com.example.playlistmaker.ui.media.favorite_tracks.view_model.FavoriteTracksViewModel
import com.example.playlistmaker.ui.player.PlayerActivity
import com.example.playlistmaker.ui.search.TrackAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTracksFragment : Fragment() {

    private var binding: FragmentFavoriteTracksBinding? = null
    private val viewModel: FavoriteTracksViewModel by viewModel()
    private var trackAdapter: TrackAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteTracksBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTracksRecyclerView()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initTracksRecyclerView() {
        trackAdapter = TrackAdapter(viewModel::onTrackClicked)
        binding?.rvFavoriteTracks?.adapter = trackAdapter
    }

    private fun initObservers() {
        viewModel.tracks.observe(viewLifecycleOwner) {
            trackAdapter?.updateData(it)
            binding?.apply {
                layoutNoFavoriteTracks.isVisible = it.isEmpty()
                rvFavoriteTracks.isVisible = it.isNotEmpty()
            }
        }

        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                is FavoriteTracksScreenEvent.OpenPlayerScreen -> {
                    startActivity(Intent(requireContext(), PlayerActivity()::class.java))
                }
            }
        }
    }
}