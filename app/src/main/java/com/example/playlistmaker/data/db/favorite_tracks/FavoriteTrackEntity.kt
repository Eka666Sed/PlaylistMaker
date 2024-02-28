package com.example.playlistmaker.data.db.favorite_tracks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.playlistmaker.domain.model.Track

@Entity(tableName = "favorite_tracks")
data class FavoriteTrackEntity(
    @PrimaryKey
    val id: Long,
    @ColumnInfo("track_name")
    val trackName: String,
    @ColumnInfo("artist_name")
    val artistName: String,
    @ColumnInfo("track_time_millis")
    val trackTimeMillis: Long,
    @ColumnInfo("artwork_url")
    val artworkUrl100: String,
    @ColumnInfo("primary_genre_url")
    val primaryGenreName: String,
    @ColumnInfo("collection_name")
    val collectionName: String,
    @ColumnInfo("country")
    val country: String,
    @ColumnInfo("release_date")
    val releaseDate: String,
    @ColumnInfo("preview_url")
    val previewUrl: String,
    @ColumnInfo("created_at")
    val createdAt: Long
) {

    companion object {

        fun mapFromDomain(
            track: Track,
            createdAt: Long = 0L
        ): FavoriteTrackEntity = FavoriteTrackEntity(
            id = track.id,
            trackName = track.trackName,
            artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis,
            artworkUrl100 = track.artworkUrl100,
            primaryGenreName = track.primaryGenreName,
            collectionName = track.collectionName,
            country = track.country,
            releaseDate = track.releaseDate,
            previewUrl = track.previewUrl,
            createdAt = createdAt
        )
    }

    fun mapToDomain(): Track = Track(
        id = id,
        trackName = trackName,
        artistName = artistName,
        trackTimeMillis = trackTimeMillis,
        artworkUrl100 = artworkUrl100,
        primaryGenreName = primaryGenreName,
        collectionName = collectionName,
        country = country,
        releaseDate = releaseDate,
        previewUrl = previewUrl,
        isFavorite = true
    )
}
