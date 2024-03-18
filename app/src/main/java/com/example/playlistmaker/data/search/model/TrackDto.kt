package com.example.playlistmaker.data.search.model

import com.example.playlistmaker.domain.model.Track
import com.google.gson.annotations.SerializedName

data class TrackDto(
    @SerializedName("trackId") val id: Long,
    @SerializedName("trackName") val trackName: String,
    @SerializedName("artistName") val artistName: String,
    @SerializedName("trackTimeMillis") val trackTimeMillis: Long,
    @SerializedName("artworkUrl100") val artworkUrl100: String,
    @SerializedName("artworkUrl60") val artworkUrl60: String,
    @SerializedName("primaryGenreName") val primaryGenreName: String,
    @SerializedName("collectionName") val collectionName: String,
    @SerializedName("country") val country: String,
    @SerializedName("releaseDate") val releaseDate: String,
    @SerializedName("previewUrl") val previewUrl:String
){

    fun mapToDomain(): Track = Track(
        id = id,
        trackName = trackName,
        artistName = artistName,
        trackTimeMillis = trackTimeMillis,
        artworkUrl100 = artworkUrl100,
        artworkUrl60 = artworkUrl60,
        primaryGenreName = primaryGenreName,
        collectionName = collectionName,
        country = country,
        releaseDate = releaseDate,
        previewUrl = previewUrl
    )
}
