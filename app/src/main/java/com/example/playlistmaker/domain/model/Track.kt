package com.example.playlistmaker.domain.model

data class Track(
    val id:Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val artworkUrl100: String,
    val primaryGenreName: String,
    val collectionName: String,
    val country: String,
    val releaseDate: String,
    val previewUrl: String,
    val isFavorite: Boolean = false
)
