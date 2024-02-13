package com.example.playlistmaker.domain.favorite_tracks.impl

import com.example.playlistmaker.data.favorite_tracks.FavoriteTracksRepository
import com.example.playlistmaker.data.search.TracksRepository
import com.example.playlistmaker.domain.favorite_tracks.FavoriteTracksInteractor
import com.example.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.Flow

class FavoriteTracksInteractorImpl(
    private val favoriteTracksRepository: FavoriteTracksRepository,
    private val trackRepository: TracksRepository
): FavoriteTracksInteractor {
    override suspend fun addToFavorites(track: Track) {
        favoriteTracksRepository.addTrackToFavorites(track)
    }

    override suspend fun deleteFromFavorites(track: Track) {
        favoriteTracksRepository.deleteTrackFromFavorites(track)
    }

    override suspend fun getFavoriteTracks(): Flow<List<Track>> {
        return favoriteTracksRepository.getFavoriteTracks()
    }

    override fun saveTrackForPlaying(track: Track) = trackRepository.saveTrackForPlaying(track)
}