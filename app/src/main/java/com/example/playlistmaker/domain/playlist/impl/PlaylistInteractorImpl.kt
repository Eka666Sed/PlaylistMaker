package com.example.playlistmaker.domain.playlist.impl

import android.net.Uri
import com.example.playlistmaker.data.external_storage.ExternalStorageRepository
import com.example.playlistmaker.data.playlist.PlaylistRepository
import com.example.playlistmaker.data.search.TracksRepository
import com.example.playlistmaker.domain.model.Playlist
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.playlist.PlaylistInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import java.util.UUID

class PlaylistInteractorImpl(
    private val externalStorageRepository: ExternalStorageRepository,
    private val playlistRepository: PlaylistRepository,
    private val trackRepository: TracksRepository
) : PlaylistInteractor {

    override suspend fun addPlaylist(name: String, description: String?, coverUri: Uri?) {
        val id = UUID.randomUUID().toString()
        val playlistCoverUri = coverUri?.let {
            externalStorageRepository.savePlaylistCover(id, coverUri)
        }
        val playlist = Playlist(
            id = id,
            name = name,
            description = description,
            coverUri = playlistCoverUri
        )
        playlistRepository.addPlaylist(playlist)
    }

    override fun getPlaylists(): Flow<List<Playlist>> = playlistRepository.getPlaylistsFlow()

    override suspend fun addTrackToPlaylist(playlist: Playlist, track: Track) {
        playlistRepository.addTrackToPlaylist(playlist, track)
    }

    override fun getPlaylistById(playlistId: String): Flow<Playlist> {
        return playlistRepository.getPlaylistById(playlistId).filterNotNull()
    }

    override fun getPlaylistTracks(tracksIds: List<Long>): Flow<List<Track>> {
        return playlistRepository.getPlaylistTracks(tracksIds)
    }

    override fun saveTrackForPlaying(track: Track) = trackRepository.saveTrackForPlaying(track)

    override suspend fun deleteTrackFromPlaylist(playlist: Playlist, trackId: Long) {
        val newPlaylistTracksIds = playlist.tracksIds
            .toMutableList()
            .apply { remove(trackId) }
        val updatedPlaylist = playlist.copy(
            tracksIds = newPlaylistTracksIds,
            tracksCount = playlist.tracksCount - 1
        )
        playlistRepository.updatePlaylist(updatedPlaylist)
        checkOtherPlaylistsContainTrack(trackId)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlistRepository.deletePlaylist(playlist)
        playlist.tracksIds.forEach { checkOtherPlaylistsContainTrack(it) }
    }

    override suspend fun updatePlaylist(playlist: Playlist, coverUri: Uri?) {
        val playlistCoverUri = if (playlist.coverUri != coverUri.toString())
            coverUri?.let { externalStorageRepository.savePlaylistCover(playlist.id, coverUri)}
        else
            playlist.coverUri
        playlistRepository.updatePlaylist(playlist.copy(coverUri = playlistCoverUri))
    }

    private suspend fun checkOtherPlaylistsContainTrack(trackId: Long) {
        val playlistsContainTrack = playlistRepository.getPlaylists().filter {
            trackId in it.tracksIds.toSet()
        }
        if (playlistsContainTrack.isEmpty()) {
            trackRepository.deleteTrack(trackId)
        }
    }
}