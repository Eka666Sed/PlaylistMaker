package com.example.playlistmaker.domain.search

import com.example.playlistmaker.data.player.Track
import com.example.playlistmaker.domain.Resource


interface TracksInteractor {
    fun searchTracks(text: String, consumer: TracksConsumer)

    interface TracksConsumer {
        fun consume(foundTracks: Resource<List<Track>>)
    }
}



