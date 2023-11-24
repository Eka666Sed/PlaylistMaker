package com.example.playlistmaker.domain.utils

import com.example.playlistmaker.domain.models.Track

class SharedPreferenceConverterInteractorImpl(
    private val converter: SharedPreferenceConverterRepository
) : SharedPreferenceConverterInteractor {
    override fun createTracksListFromJson(json: String): Array<Track> {
        return converter.createTracksListFromJson(json)
    }

    override fun createJsonFromTracksList(facts: List<Track>): String {
        return converter.createJsonFromTracksList(facts)
    }

    override fun createJsonFromTrack(fact: Track): String {
        return converter.createJsonFromTrack(fact)
    }

    override fun createTrackFromJson(json: String): Track {
        return converter.createTrackFromJson(json)
    }
}
