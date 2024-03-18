package com.example.playlistmaker.ui.media.playlists

sealed class PlaylistsScreenEvent{
    object NavigateToNewPlaylist : PlaylistsScreenEvent()
    data class NavigateToPlaylistInfo(val playlistId: String) : PlaylistsScreenEvent()
}
