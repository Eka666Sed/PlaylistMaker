package com.example.playlistmaker.domain.search

import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SearchInteractor {
    fun searchTracks(request: String): Flow<Resource<List<Track>>>
    fun getTracksHistory(): List<Track>
    fun addTrackToHistory(track: Track)
    fun clearTrackHistory()
    fun saveTrackForPlaying(track: Track)
}