package com.example.playlistmaker.data.search

import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.utils.Resource

interface TracksRepository {
    fun searchTracks(text: String): Resource<List<Track>>
    fun getTracksHistory(): List<Track>
    fun addTrackToHistory(track: Track)
    fun clearTrackHistory()
    fun getTrackForPlaying(): Track?
    fun saveTrackForPlaying(track: Track?)
}