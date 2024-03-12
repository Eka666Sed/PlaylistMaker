package com.example.playlistmaker.ui.media.playlist_info

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentPlaylistInfoBinding
import com.example.playlistmaker.domain.model.Playlist
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.ui.media.playlist_info.menu.PlaylistInfoMenuItemAdapter
import com.example.playlistmaker.ui.media.playlist_info.view_model.PlaylistInfoViewModel
import com.example.playlistmaker.ui.player.PlayerActivity
import com.example.playlistmaker.ui.search.TrackAdapter
import com.example.playlistmaker.ui.util.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistInfoFragment : Fragment() {

    private var binding: FragmentPlaylistInfoBinding? = null
    private val viewModel: PlaylistInfoViewModel by viewModel()
    private lateinit var trackAdapter: TrackAdapter
    private val menuAdapter: PlaylistInfoMenuItemAdapter = PlaylistInfoMenuItemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistInfoBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBackPressHandling()
        initButtons()
        initTracksBottomSheet()
        initMenuBottomSheet()
        initObservers()
    }

    private fun initBackPressHandling() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isEnabled) {
                    viewModel.onBackPressed()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun initButtons() {
        binding?.apply {
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            btnShare.setOnClickListener { viewModel.onShareButtonClicked() }
            btnMenu.setOnClickListener { viewModel.onMenuButtonClicked() }
        }
    }

    private fun initTracksBottomSheet() {
        binding?.let {
            BottomSheetBehavior.from(it.bottomSheetTracks).apply {
                addBottomSheetCallback(
                    object : BottomSheetBehavior.BottomSheetCallback() {

                        override fun onStateChanged(bottomSheet: View, newState: Int) {
                        }

                        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
                    })
            }
            trackAdapter = TrackAdapter(viewModel::onTrackClicked, viewModel::onTrackLongClicked)
            it.rvTracks.adapter = trackAdapter
        }
    }

    private fun initMenuBottomSheet() {
        binding?.let {
            BottomSheetBehavior.from(it.bottomSheetMenu).apply {
                state = BottomSheetBehavior.STATE_HIDDEN
                addBottomSheetCallback(
                    object : BottomSheetBehavior.BottomSheetCallback() {

                        override fun onStateChanged(bottomSheet: View, newState: Int) {
                            it.overlay.isVisible = newState != BottomSheetBehavior.STATE_HIDDEN
                        }

                        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
                    })
            }
            it.rvMenu.adapter = menuAdapter
        }
    }

    private fun initObservers() {
        viewModel.screenState.observe(viewLifecycleOwner) {
            it.apply {
                setPlaylistInfo(
                    playlistCoverUri,
                    playlistName,
                    playlistDescription,
                    playlistDuration,
                    tracksCount
                )
                setTracksInfo(tracks)
                setMenuInfo(playlistCoverUri, playlistName, tracksCount)
            }
        }

        viewModel.menuItems.observe(viewLifecycleOwner) { menuAdapter.updateItems(it) }

        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                PlaylistInfoScreenEvent.NavigateBack -> findNavController().popBackStack()
                is PlaylistInfoScreenEvent.SetMenuVisibility -> setMenuVisible(it.isVisible)
                PlaylistInfoScreenEvent.ShowNoTracksInPlaylistMessage -> {
                    showNoTracksInPlaylistMessage()
                }

                PlaylistInfoScreenEvent.OpenPlayerScreen -> {
                    startActivity(Intent(requireContext(), PlayerActivity()::class.java))
                }

                PlaylistInfoScreenEvent.ShowDeleteTrackDialog -> showDeleteTrackDialog()
                is PlaylistInfoScreenEvent.ShowDeletePlaylistDialog -> {
                    showDeletePlaylistDialog(it.playlistName)
                }

                is PlaylistInfoScreenEvent.NavigateToEditPlaylist -> {
                    navigateToEditPlaylist(it.playlist)
                }
            }
        }
    }

    private fun setPlaylistInfo(
        coverUri: String?,
        name: String,
        description: String,
        tracksDuration: Int,
        tracksCount: Int
    ) {
        binding?.apply {
            Glide.with(requireContext())
                .load(coverUri)
                .into(ivCover)
            tvName.text = name
            tvDescription.text = description
            tvTracksInfo.text = getTracksInfoText(tracksDuration, tracksCount)
        }
    }

    private fun setTracksInfo(tracks: List<Track>) {
        trackAdapter.updateData(tracks)
        binding?.apply {
            rvTracks.isVisible = tracks.isNotEmpty()
            lytNoTracks.isVisible = tracks.isEmpty()
        }
    }

    private fun setMenuInfo(coverUri: String?, name: String, tracksCount: Int) {
        binding?.apply {
            coverUri?.let { ivCoverMenu.load(it) }
            tvNameMenu.text = name
            tvTracksCount.text = "$tracksCount " +
                    resources.getQuantityString(R.plurals.track, tracksCount, tracksCount)
        }
    }

    private fun getTracksInfoText(tracksDuration: Int, tracksCount: Int): String {
        val tracksDurationText = "$tracksDuration " +
                resources.getQuantityString(R.plurals.minute, tracksDuration, tracksDuration)
        val tracksCountText = "$tracksCount " +
                resources.getQuantityString(R.plurals.track, tracksCount, tracksCount)
        return getString(R.string.playlist_tracks_info, tracksDurationText, tracksCountText)
    }

    private fun navigateToEditPlaylist(playlist: Playlist) {
        val action =
            PlaylistInfoFragmentDirections.actionPlaylistInfoFragmentToEditPlaylistFragment(playlist)
        findNavController().navigate(action)
    }

    private fun showDeleteTrackDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_track_dialog_title))
            .setMessage(getString(R.string.delete_track_dialog_message))
            .setNegativeButton(getString(R.string.confirmation_dialog_negative)) { dialog, _ -> viewModel.onDeleteTrackCanceled() }
            .setPositiveButton(getString(R.string.delete_track_dialog_positive)) { _, _ -> viewModel.onDeleteTrackConfirmed() }
            .show()
    }

    private fun showDeletePlaylistDialog(playlistName: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_playlist_dialog_title))
            .setMessage(getString(R.string.delete_playlist_dialog_message, playlistName))
            .setNegativeButton(getString(R.string.delete_playlist_dialog_negative)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.delete_playlist_dialog_positive)) { _, _ ->
                viewModel.onDeletePlaylistConfirmed()
            }
            .show()
    }

    private fun setMenuVisible(isVisible: Boolean) {
        binding?.bottomSheetMenu?.let { layout ->
            val bottomSheetBehavior = BottomSheetBehavior.from(layout)
            bottomSheetBehavior.state =
                if (isVisible) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun showNoTracksInPlaylistMessage() {
        Toast.makeText(
            requireContext(),
            getString(R.string.no_tracks_in_playlist),
            Toast.LENGTH_SHORT
        ).show()
    }
}