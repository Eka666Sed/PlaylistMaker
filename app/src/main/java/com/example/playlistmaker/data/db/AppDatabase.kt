package com.example.playlistmaker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(version = 1, entities = [FavoriteTrackEntity::class])

abstract class AppDatabase : RoomDatabase(){

    abstract fun favoriteTrackDao(): FavoriteTrackDao

    companion object {
        const val NAME = "playlist_maker_database"
    }

}