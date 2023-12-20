package com.example.playlistmaker.domain.search

import com.example.playlistmaker.data.player.Track
import com.example.playlistmaker.domain.Resource

interface TracksRepository {
    fun searchTracks(text: String): Resource<List<Track>>
}