package com.example.playlistmaker.domain.search.impl

import com.example.playlistmaker.data.search.TracksRepository
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.search.SearchInteractor
import com.example.playlistmaker.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class SearchInteractorImpl(private val trackRepository: TracksRepository) : SearchInteractor {

    override fun searchTracks(request: String): Flow<Resource<List<Track>>> {
       return trackRepository.searchTracks(request)
    }

    override fun getTracksHistory(): List<Track> = trackRepository.getTracksHistory()

    override fun addTrackToHistory(track: Track) = trackRepository.addTrackToHistory(track)

    override fun clearTrackHistory() = trackRepository.clearTrackHistory()

    override fun saveTrackForPlaying(track: Track) = trackRepository.saveTrackForPlaying(track)
}