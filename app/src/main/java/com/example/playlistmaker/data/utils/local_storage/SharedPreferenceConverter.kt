package com.example.playlistmaker.data.utils.local_storage

import com.example.playlistmaker.domain.models.Track
import com.google.gson.Gson

object SharedPreferenceConverter {
    fun createTracksListFromJson(json: String): Array<Track/*Dto*/> {
        return Gson().fromJson(json, Array<Track/*Dto*/>::class.java)
    }

    fun createJsonFromTracksList(facts: List<Track/*Dto*/>): String {
        return Gson().toJson(facts)
    }

    fun createJsonFromTrack(fact: Track/*Dto*/): String {
        return Gson().toJson(fact)
    }

    fun createTrackFromJson(json: String): Track/*Dto*/ {
        return Gson().fromJson(json, Track/*Dto*/::class.java)
    }

}