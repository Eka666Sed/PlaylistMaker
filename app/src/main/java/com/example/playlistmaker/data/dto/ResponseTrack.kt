package com.example.playlistmaker.data.dto


data class ResponseTrack(
    val resultCount: Int,
    val results: List<TrackDto>
): Response()
