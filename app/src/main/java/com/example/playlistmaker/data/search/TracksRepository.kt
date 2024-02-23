package com.example.playlistmaker.data.search

import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    fun searchTracks(text: String): Flow<Resource<List<Track>>>
    fun getTracksHistory(): List<Track>
    fun addTrackToHistory(track: Track)
    fun clearTrackHistory()
    fun getTrackForPlaying(): Track?
    fun saveTrackForPlaying(track: Track?)
}