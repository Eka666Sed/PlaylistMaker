package com.example.playlistmaker.ui.media.playlist_info

import com.example.playlistmaker.domain.model.Playlist

sealed class PlaylistInfoScreenEvent{
    object NavigateBack : PlaylistInfoScreenEvent()
    data class SetMenuVisibility(val isVisible: Boolean) : PlaylistInfoScreenEvent()
    object ShowNoTracksInPlaylistMessage : PlaylistInfoScreenEvent()
    object OpenPlayerScreen : PlaylistInfoScreenEvent()
    object ShowDeleteTrackDialog : PlaylistInfoScreenEvent()
    data class ShowDeletePlaylistDialog(val playlistName: String) : PlaylistInfoScreenEvent()
    data class NavigateToEditPlaylist(val playlist: Playlist) : PlaylistInfoScreenEvent()
}
