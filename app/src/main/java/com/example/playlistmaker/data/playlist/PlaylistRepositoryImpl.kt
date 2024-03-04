package com.example.playlistmaker.data.playlist

import com.example.playlistmaker.data.db.playlist.PlaylistDao
import com.example.playlistmaker.data.db.playlist.PlaylistEntity
import com.example.playlistmaker.data.db.track.TrackDao
import com.example.playlistmaker.data.db.track.TrackEntity
import com.example.playlistmaker.domain.model.Playlist
import com.example.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistRepositoryImpl(
    private val playlistDao: PlaylistDao,
    private val trackDao: TrackDao
) : PlaylistRepository {

    override suspend fun addPlaylist(playlist: Playlist) {
        playlistDao.addPlaylist(PlaylistEntity.mapFromDomain(playlist))
    }

    override fun getPlaylists(): Flow<List<Playlist>> = playlistDao.getPlaylists().map {
        it.map { entity -> entity.mapToDomain() }
    }

    override suspend fun updatePlaylist(playlist: Playlist, track: Track) {
        val tracksIds = playlist.tracksIds.toMutableList()
        tracksIds.add(track.id)
        val newPlaylist =
            playlist.copy(tracksIds = tracksIds, tracksCount = playlist.tracksCount + 1)
        playlistDao.updatePlaylist(PlaylistEntity.mapFromDomain(newPlaylist))
        trackDao.addTrack(TrackEntity.mapFromDomain(track))
    }
}