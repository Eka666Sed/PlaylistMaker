package com.example.playlistmaker.di

import com.example.playlistmaker.domain.favorite_tracks.FavoriteTracksInteractor
import com.example.playlistmaker.domain.favorite_tracks.impl.FavoriteTracksInteractorImpl
import com.example.playlistmaker.domain.player.PlayerInteractor
import com.example.playlistmaker.domain.player.impl.PlayerInteractorImpl
import com.example.playlistmaker.domain.search.SearchInteractor
import com.example.playlistmaker.domain.search.impl.SearchInteractorImpl
import com.example.playlistmaker.domain.settings.SettingsInteractor
import com.example.playlistmaker.domain.settings.impl.SettingsInteractorImpl
import com.example.playlistmaker.domain.sharing.SharingInteractor
import com.example.playlistmaker.domain.sharing.impl.SharingInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<SearchInteractor> {
        SearchInteractorImpl(trackRepository = get())
    }

    single<PlayerInteractor> {
        PlayerInteractorImpl(trackRepository = get())
    }

    single<SettingsInteractor>(createdAtStart = true) {
        SettingsInteractorImpl(settingsRepository = get())
    }

    single<SharingInteractor> {
        SharingInteractorImpl(externalNavigator = get())
    }

    single<FavoriteTracksInteractor> {
        FavoriteTracksInteractorImpl(favoriteTracksRepository = get(), trackRepository = get())
    }
}