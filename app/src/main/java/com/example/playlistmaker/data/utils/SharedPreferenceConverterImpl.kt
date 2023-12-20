package com.example.playlistmaker.data.utils

import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.utils.SharedPreferenceConverter
import com.google.gson.Gson

class SharedPreferenceConverterImpl : SharedPreferenceConverter {


    override fun convertJsonToList(json: String): List<Track> {
        return Gson().fromJson(json, Array<Track>::class.java).toList()
    }

    override fun convertListToJson(tracks: List<Track>): String = Gson().toJson(tracks)

    override fun convertTrackToJson(track: Track): String = Gson().toJson(track)

    override fun convertJsonToTrack(json: String): Track = Gson().fromJson(json, Track::class.java)
}