package com.example.playlistmaker.ui.media.playlist_info

import com.example.playlistmaker.domain.model.Track

data class PlaylistInfoScreenState(
    val playlistCoverUri: String?,
    val playlistName: String,
    val playlistDescription: String,
    val tracksCount: Int,
    val playlistDuration: Int,
    val tracks: List<Track>
)
