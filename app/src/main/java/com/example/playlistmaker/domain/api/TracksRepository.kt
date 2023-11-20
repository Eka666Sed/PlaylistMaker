package com.example.playlistmaker.domain.api

import com.example.playlistmaker.data.dto.Response
import com.example.playlistmaker.domain.models.Track

interface TracksRepository {
    fun searchTracks(text: String): List<Track>
    fun getRequestStatus() : Response
}