package com.example.playlistmaker.di

import com.example.playlistmaker.data.favorite_tracks.FavoriteTracksRepository
import com.example.playlistmaker.data.favorite_tracks.impl.FavoriteTracksRepositoryImpl
import com.example.playlistmaker.data.search.TracksRepository
import com.example.playlistmaker.data.search.impl.TracksRepositoryImpl
import com.example.playlistmaker.data.settings.SettingsRepository
import com.example.playlistmaker.data.settings.impl.SettingsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<SettingsRepository> {
        SettingsRepositoryImpl(sharedPreferences = get())
    }

    single<TracksRepository> {
        TracksRepositoryImpl(
            networkClient = get(),
            sharedPreferences = get(),
            sharedPreferencesConverter = get(),
            favoriteTrackDao = get()
        )
    }

    single<FavoriteTracksRepository> {
        FavoriteTracksRepositoryImpl(favoriteTrackDao = get())
    }
}