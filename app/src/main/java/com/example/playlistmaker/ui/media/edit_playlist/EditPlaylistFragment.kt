package com.example.playlistmaker.ui.media.edit_playlist

import android.os.Bundle
import android.view.View
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.media.create_playlist.CreatePlaylistFragment

class EditPlaylistFragment : CreatePlaylistFragment() {

    override val viewModel: EditPlaylistViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.playlist.observe(viewLifecycleOwner) { playlist ->
            binding?.apply {
                editTextName.setText(playlist.name)
                editTextDescription.setText(playlist.description)
            }
        }
    }

    override fun initToolbar() {
        super.initToolbar()
        binding?.apply { toolbar.setTitle(R.string.edit) }
    }

    override fun initCompleteButton() {
        super.initCompleteButton()
        binding?.apply { btnComplete.setText(R.string.save) }
    }
}