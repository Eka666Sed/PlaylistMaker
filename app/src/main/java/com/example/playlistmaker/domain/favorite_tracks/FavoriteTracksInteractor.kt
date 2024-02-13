package com.example.playlistmaker.domain.favorite_tracks

import com.example.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteTracksInteractor {
    suspend fun addToFavorites(track: Track)
    suspend fun deleteFromFavorites(track: Track)
    suspend fun getFavoriteTracks(): Flow<List<Track>>
    fun saveTrackForPlaying(track: Track)
}