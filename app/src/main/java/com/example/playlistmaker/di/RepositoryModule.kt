package com.example.playlistmaker.di

import com.example.playlistmaker.data.external_storage.ExternalStorageRepository
import com.example.playlistmaker.data.external_storage.ExternalStorageRepositoryImpl
import com.example.playlistmaker.data.favorite_tracks.FavoriteTracksRepository
import com.example.playlistmaker.data.favorite_tracks.impl.FavoriteTracksRepositoryImpl
import com.example.playlistmaker.data.main.NavigationRepository
import com.example.playlistmaker.data.main.impl.NavigationRepositoryImpl
import com.example.playlistmaker.data.playlist.PlaylistRepository
import com.example.playlistmaker.data.playlist.PlaylistRepositoryImpl
import com.example.playlistmaker.data.search.TracksRepository
import com.example.playlistmaker.data.search.impl.TracksRepositoryImpl
import com.example.playlistmaker.data.settings.SettingsRepository
import com.example.playlistmaker.data.settings.impl.SettingsRepositoryImpl
import org.koin.android.ext.koin.androidContext
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
            favoriteTrackDao = get(),
            trackDao = get()
        )
    }

    single<FavoriteTracksRepository> {
        FavoriteTracksRepositoryImpl(favoriteTrackDao = get())
    }

    single<NavigationRepository> { NavigationRepositoryImpl() }

    single<PlaylistRepository> {
        PlaylistRepositoryImpl(playlistDao = get(), trackDao = get())
    }

    single<ExternalStorageRepository> {
        ExternalStorageRepositoryImpl(context = androidContext())
    }
}