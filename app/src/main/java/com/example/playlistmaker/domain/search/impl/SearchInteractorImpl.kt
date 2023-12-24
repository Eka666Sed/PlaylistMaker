package com.example.playlistmaker.domain.search.impl

import com.example.playlistmaker.data.search.TracksRepository
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.search.SearchInteractor
import java.util.concurrent.Executors

class SearchInteractorImpl(private val trackRepository: TracksRepository) : SearchInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchTracks(request: String, consumer: SearchInteractor.TracksConsumer) {
        executor.execute {
            consumer.consume(trackRepository.searchTracks(request))
        }
    }

    override fun getTracksHistory(): List<Track> = trackRepository.getTracksHistory()

    override fun addTrackToHistory(track: Track) = trackRepository.addTrackToHistory(track)

    override fun clearTrackHistory() = trackRepository.clearTrackHistory()

    override fun saveTrackForPlaying(track: Track) = trackRepository.saveTrackForPlaying(track)
}