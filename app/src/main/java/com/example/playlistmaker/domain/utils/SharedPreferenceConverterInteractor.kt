package com.example.playlistmaker.domain.utils

import com.example.playlistmaker.domain.models.Track

interface SharedPreferenceConverterInteractor {
    fun createTracksListFromJson(json: String): Array<Track>
    fun createJsonFromTracksList(facts: List<Track>): String
    fun createJsonFromTrack(fact: Track): String
    fun createTrackFromJson(json: String): Track
}