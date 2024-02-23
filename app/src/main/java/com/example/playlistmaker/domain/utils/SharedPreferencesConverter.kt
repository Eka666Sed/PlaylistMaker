package com.example.playlistmaker.domain.utils

import com.example.playlistmaker.domain.model.Track

interface SharedPreferencesConverter {
    fun convertJsonToList(json: String): List<Track>
    fun convertListToJson(tracks: List<Track>): String
    fun convertTrackToJson(track: Track): String
    fun convertJsonToTrack(json: String): Track
}