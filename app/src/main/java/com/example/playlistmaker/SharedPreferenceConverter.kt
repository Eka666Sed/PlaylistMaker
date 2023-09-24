package com.example.playlistmaker

import com.google.gson.Gson

object SharedPreferenceConverter {
    fun createTracksListFromJson(json: String): Array<Track> {
        return Gson().fromJson(json, Array<Track>::class.java)
    }

    fun createJsonFromTracksList(facts: List<Track>): String {
        return Gson().toJson(facts)
    }

    fun createJsonFromTrack(fact: Track): String {
        return Gson().toJson(fact)
    }

    fun createTrackFromJson(json: String): Track {
        return Gson().fromJson(json, Track::class.java)
    }


}