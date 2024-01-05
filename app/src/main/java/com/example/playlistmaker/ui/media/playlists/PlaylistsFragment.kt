package com.example.playlistmaker.ui.media.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.FragmentPlaylistsBinding
import com.example.playlistmaker.ui.media.playlists.view_model.PlaylistsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment : Fragment() {

    private var binding: FragmentPlaylistsBinding? = null
    private val viewModel: PlaylistsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistsBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.playlists.observe(viewLifecycleOwner) {
            binding?.layoutNoPlaylists?.isVisible = it.isEmpty()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}