package com.example.playlistmaker.ui.media.playlists.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.main.NavigationInteractor
import com.example.playlistmaker.domain.model.Playlist
import com.example.playlistmaker.domain.playlist.PlaylistInteractor
import com.example.playlistmaker.ui.media.playlists.PlaylistsScreenEvent
import com.example.playlistmaker.ui.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    private val navigationInteractor: NavigationInteractor,
    private val playlistInteractor: PlaylistInteractor
) : ViewModel() {

    private val _playlists = MutableLiveData<List<Playlist>>(listOf())
    val playlists: LiveData<List<Playlist>> = _playlists

    val event = SingleLiveEvent<PlaylistsScreenEvent>()

    init {
        subscribeOnPlaylists()
    }

    fun onNewPlaylistButtonClicked() {
        navigationInteractor.setBottomNavigationVisibility(false)
        event.value = PlaylistsScreenEvent.NavigateToNewPlaylist
    }

    private fun subscribeOnPlaylists() {
        viewModelScope.launch(Dispatchers.IO) {
            playlistInteractor.getPlaylists().collect { _playlists.postValue(it) }
        }
    }
}