package com.example.playlistmaker.ui.search

import com.example.playlistmaker.domain.models.Track

data class SearchScreenState(
    val clearButtonVisible: Boolean = false,
    val tracks: List<Track> = listOf(),
    val tracksHistory: List<Track> = listOf(),
    val tracksHistoryVisible: Boolean = false,
    val messageVisible: Boolean = false,
    val noWebVisible: Boolean = false,
    val noTracksVisible: Boolean = false,
    val progressVisible: Boolean = false
)
