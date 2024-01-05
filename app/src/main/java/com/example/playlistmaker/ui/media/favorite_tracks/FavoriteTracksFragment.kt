package com.example.playlistmaker.ui.media.favorite_tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.FragmentFavoriteTracksBinding
import com.example.playlistmaker.ui.media.favorite_tracks.view_model.FavoriteTracksViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTracksFragment : Fragment() {

    private var binding: FragmentFavoriteTracksBinding? = null
    private val viewModel: FavoriteTracksViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteTracksBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tracks.observe(viewLifecycleOwner) {
            binding?.layoutNoFavoriteTracks?.isVisible = it.isEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}