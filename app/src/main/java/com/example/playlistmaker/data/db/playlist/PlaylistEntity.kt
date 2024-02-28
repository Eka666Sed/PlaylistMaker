package com.example.playlistmaker.data.db.playlist

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.playlistmaker.domain.model.Playlist

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String?,
    val coverUri: String?,
    val tracksIds: List<Long> = listOf(),
    val tracksCount: Int = 0
) {

    companion object {
        fun mapFromDomain(playlist: Playlist) = PlaylistEntity(
            id = playlist.id,
            name = playlist.name,
            description = playlist.description,
            coverUri = playlist.coverUri,
            tracksIds = playlist.tracksIds,
            tracksCount = playlist.tracksCount
        )
    }

    fun mapToDomain(): Playlist = Playlist(
        id = id,
        name = name,
        description = description,
        coverUri = coverUri,
        tracksIds = tracksIds,
        tracksCount = tracksCount
    )
}