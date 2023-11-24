package com.example.playlistmaker.data.utils.local_storage

import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.utils.SharedPreferenceConverterRepository
import com.google.gson.Gson

class SharedPreferenceConverterImpl : SharedPreferenceConverterRepository {

    override fun createTracksListFromJson(json: String): Array<Track> {
        return Gson().fromJson(json, Array<Track>::class.java)
    }

    override fun createJsonFromTracksList(facts: List<Track>): String {
        return Gson().toJson(facts)
    }

    override fun createJsonFromTrack(fact: Track): String {
        return Gson().toJson(fact)
    }

    override fun createTrackFromJson(json: String): Track {
        return Gson().fromJson(json, Track::class.java)
    }
}