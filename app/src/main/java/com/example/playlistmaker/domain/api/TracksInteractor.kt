package com.example.playlistmaker.domain.api

import com.example.playlistmaker.data.dto.Response
import com.example.playlistmaker.domain.models.Track

interface TracksInteractor {
    fun searchTracks(text: String, consumer: TracksConsumer)
    fun getRequestStatus() : Response

    interface TracksConsumer {
        fun consume(foundTracks: List<Track>)
    }
}