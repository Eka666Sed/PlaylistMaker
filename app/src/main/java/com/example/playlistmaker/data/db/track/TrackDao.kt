package com.example.playlistmaker.data.db.track

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTrack(track: TrackEntity)

    @Query("DELETE FROM tracks WHERE id = (:trackId)")
    suspend fun deleteTrack(trackId: Long)

    @Query("DELETE FROM tracks WHERE id = (:tracksIds)")
    suspend fun deleteTracks(tracksIds: List<Long>)

    @Query("SELECT * FROM tracks WHERE id IN (:tracksIds)")
    suspend fun getPlaylistTracks(tracksIds: List<Long>): List<TrackEntity>
}