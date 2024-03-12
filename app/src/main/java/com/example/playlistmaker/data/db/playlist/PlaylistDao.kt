package com.example.playlistmaker.data.db.playlist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlaylist(playlist: PlaylistEntity)

    @Query("SELECT * FROM playlists")
    fun getPlaylistsFlow(): Flow<List<PlaylistEntity>>

    @Query("SELECT * FROM playlists")
    fun getPlaylists(): List<PlaylistEntity>

    @Update
    fun updatePlaylist(playlist: PlaylistEntity)

    @Query("SELECT * FROM playlists WHERE id = :playlistId")
    fun getPlaylistById(playlistId: String): Flow<PlaylistEntity?>

    @Delete
    suspend fun deletePlaylist(playlist: PlaylistEntity)
}