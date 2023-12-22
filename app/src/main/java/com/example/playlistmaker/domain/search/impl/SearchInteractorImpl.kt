package com.example.playlistmaker.domain.search.impl

import com.example.playlistmaker.data.search.TracksRepository
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.search.SearchInteractor
import java.util.concurrent.Executors

class SearchInteractorImpl(private val repository: TracksRepository) : SearchInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchTracks(request: String, consumer: SearchInteractor.TracksConsumer) {
        executor.execute {
            consumer.consume(repository.searchTracks(request))
        }
    }

    override fun getTracksHistory(): List<Track> = repository.getTracksHistory()

    override fun addTrackToHistory(track: Track) = repository.addTrackToHistory(track)

    override fun clearTrackHistory() = repository.clearTrackHistory()

    override fun saveTrackForPlaying(track: Track) = repository.saveTrackForPlaying(track)
}