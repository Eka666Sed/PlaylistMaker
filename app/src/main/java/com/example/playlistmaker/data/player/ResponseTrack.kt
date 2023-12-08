package com.example.playlistmaker.data.player


data class ResponseTrack(
    val resultCount: Int,
    val results: List<TrackDto>
): Response()
