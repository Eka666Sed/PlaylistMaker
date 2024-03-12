package com.example.playlistmaker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.playlistmaker.data.db.favorite_tracks.FavoriteTrackDao
import com.example.playlistmaker.data.db.favorite_tracks.FavoriteTrackEntity
import com.example.playlistmaker.data.db.playlist.PlaylistDao
import com.example.playlistmaker.data.db.playlist.PlaylistEntity
import com.example.playlistmaker.data.db.track.TrackDao
import com.example.playlistmaker.data.db.track.TrackEntity
import com.example.playlistmaker.data.db.util.ListConverter


@Database(
    entities = [FavoriteTrackEntity::class, PlaylistEntity::class, TrackEntity::class],
    version = 8
)
@TypeConverters(ListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteTrackDao(): FavoriteTrackDao

    abstract fun playlistDao(): PlaylistDao

    abstract fun trackDao(): TrackDao

    companion object {
        const val NAME = "playlist_maker_database"
    }
}