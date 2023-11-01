package com.example.playlistmaker

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

data class Track(
    @SerializedName("trackName") val trackName: String,
    @SerializedName("artistName") val artistName: String,
    @SerializedName("trackTimeMillis") val trackTimeMillis: Long,
    @SerializedName("artworkUrl100") val artworkUrl100: String,
    @SerializedName("primaryGenreName") val primaryGenreName: String,
    @SerializedName("collectionName") val collectionName: String,
    @SerializedName("country") val country: String,
    @SerializedName("releaseDate") val releaseDate: String,
    @SerializedName("previewUrl") val previewUrl:String

) {
    fun getFormattedTrackTime(): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis)
    }

    fun getYearFormReleaseDate(): String = releaseDate.substring(0, 4)

    fun getImageNeedSize(): String = artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")

}
