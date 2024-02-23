package com.example.playlistmaker.data.utils

import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.utils.SharedPreferencesConverter
import com.google.gson.Gson

class SharedPreferencesConverterImpl(private val gson: Gson) : SharedPreferencesConverter {


    override fun convertJsonToList(json: String): List<Track> {
        return gson.fromJson(json, Array<Track>::class.java).toList()
    }

    override fun convertListToJson(tracks: List<Track>): String = gson.toJson(tracks)

    override fun convertTrackToJson(track: Track): String = gson.toJson(track)

    override fun convertJsonToTrack(json: String): Track = gson.fromJson(json, Track::class.java)
}