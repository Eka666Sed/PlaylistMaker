package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.utils.Resource

interface TracksRepository {
    fun searchTracks(text: String): Resource<List<Track>>
}