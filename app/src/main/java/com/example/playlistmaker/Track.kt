package com.example.playlistmaker

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

data class Track(
    @SerializedName("trackName") val trackName:String,
    @SerializedName("artistName") val artistName:String,
    @SerializedName("trackTimeMillis") val trackTimeMillis:Long,
    @SerializedName("artworkUrl100") val artworkUrl100:String
){
     fun getFormattedTrackTime(): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis)
    }
}
