package com.example.playlistmaker.domain.settings.model

import com.example.playlistmaker.data.player.Track

interface SharedPreferenceConverterRepository {
    fun createTracksListFromJson(json: String): Array<Track>
    fun createJsonFromTracksList(facts: List<Track>): String
    fun createJsonFromTrack(fact: Track): String
    fun createTrackFromJson(json: String): Track
}