package com.example.playlistmaker.data.search.model

import com.google.gson.annotations.SerializedName

data class TrackDto(
    @SerializedName("trackName") val trackName: String,
    @SerializedName("artistName") val artistName: String,
    @SerializedName("trackTimeMillis") val trackTimeMillis: Long,
    @SerializedName("artworkUrl100") val artworkUrl100: String,
    @SerializedName("primaryGenreName") val primaryGenreName: String,
    @SerializedName("collectionName") val collectionName: String,
    @SerializedName("country") val country: String,
    @SerializedName("releaseDate") val releaseDate: String,
    @SerializedName("previewUrl") val previewUrl:String
)
