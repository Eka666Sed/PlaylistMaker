package com.example.playlistmaker.data.favorite_tracks

import com.example.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteTracksRepository {
    suspend  fun addTrackToFavorites(track: Track)
    suspend  fun deleteTrackFromFavorites(track: Track)
    suspend  fun getFavoriteTracks(): Flow<List<Track>>
}