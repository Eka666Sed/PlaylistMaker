package com.example.playlistmaker.data.search.model

import com.example.playlistmaker.data.network.Response


data class ResponseTrack(
    val resultCount: Int,
    val results: List<TrackDto>
): Response()