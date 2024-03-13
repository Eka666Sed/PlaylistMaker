package com.example.playlistmaker.data.playlist

import com.example.playlistmaker.domain.model.Playlist
import com.example.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    suspend fun addPlaylist(playlist: Playlist)
    fun getPlaylistsFlow(): Flow<List<Playlist>>
    fun getPlaylists(): List<Playlist>
    suspend fun addTrackToPlaylist(playlist: Playlist, track: Track)
    fun getPlaylistById(playlistId: String): Flow<Playlist?>
    fun getPlaylistTracks(tracksIds: List<Long>): Flow<List<Track>>
    suspend fun updatePlaylist(playlist: Playlist)
    suspend fun deletePlaylist(playlist: Playlist)
}