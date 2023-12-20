package com.example.playlistmaker.domain.search

import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.utils.Resource

interface SearchInteractor {
    fun searchTracks(request: String, consumer: TracksConsumer)
    fun getTracksHistory(): List<Track>
    fun addTrackToHistory(track: Track)
    fun clearTrackHistory()
    fun saveTrackForPlaying(track: Track)

    interface TracksConsumer {
        fun consume(tracks: Resource<List<Track>>)
    }
}