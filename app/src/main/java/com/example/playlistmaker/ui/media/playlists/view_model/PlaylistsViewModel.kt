package com.example.playlistmaker.ui.media.playlists.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.model.Playlist

class PlaylistsViewModel : ViewModel() {

    private val _playlists = MutableLiveData<List<Playlist>>(listOf())
    val playlists: LiveData<List<Playlist>> = _playlists
}