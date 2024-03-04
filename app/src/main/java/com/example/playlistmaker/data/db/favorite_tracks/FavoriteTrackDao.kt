package com.example.playlistmaker.data.db.favorite_tracks

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteTrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrack(track: FavoriteTrackEntity)

    @Delete
    suspend fun deleteTrack(track: FavoriteTrackEntity)

    @Query("SELECT * FROM favorite_tracks ORDER BY created_at DESC")
    fun getTracks(): Flow<List<FavoriteTrackEntity>>

    @Query("SELECT id FROM favorite_tracks")
    fun getTracksIds(): List<Long>
}