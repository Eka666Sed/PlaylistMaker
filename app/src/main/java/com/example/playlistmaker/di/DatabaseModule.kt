package com.example.playlistmaker.di

import androidx.room.Room
import com.example.playlistmaker.data.db.AppDatabase
import com.example.playlistmaker.data.db.favorite_tracks.FavoriteTrackDao
import com.example.playlistmaker.data.db.playlist.PlaylistDao
import com.example.playlistmaker.data.db.track.TrackDao
import com.example.playlistmaker.data.db.util.ListConverter
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single<AppDatabase> {
        Room
            .databaseBuilder(androidContext(), AppDatabase::class.java, AppDatabase.NAME)
            .addTypeConverter(ListConverter(get<Gson>()))
            .fallbackToDestructiveMigration()
            .build()
    }

    single<FavoriteTrackDao> { get<AppDatabase>().favoriteTrackDao() }
    single<PlaylistDao> { get<AppDatabase>().playlistDao() }
    single<TrackDao> { get<AppDatabase>().trackDao() }
}