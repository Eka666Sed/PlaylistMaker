package com.example.playlistmaker.data.favorite_tracks.impl

import com.example.playlistmaker.data.db.FavoriteTrackDao
import com.example.playlistmaker.data.db.FavoriteTrackEntity
import com.example.playlistmaker.data.favorite_tracks.FavoriteTracksRepository
import com.example.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteTracksRepositoryImpl(
    private val favoriteTrackDao: FavoriteTrackDao
): FavoriteTracksRepository {
    override suspend fun addTrackToFavorites(track: Track) {
        favoriteTrackDao.addTrack(FavoriteTrackEntity.mapFromDomain(track, System.currentTimeMillis()))
    }

    override suspend fun deleteTrackFromFavorites(track: Track) {
        favoriteTrackDao.deleteTrack(FavoriteTrackEntity.mapFromDomain(track))
    }

    override suspend fun getFavoriteTracks(): Flow<List<Track>> {
        return favoriteTrackDao.getTracks().map {
            it.map { entity -> entity.mapToDomain() }
        }
    }
}