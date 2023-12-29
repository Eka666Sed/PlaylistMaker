package com.example.playlistmaker.di

import com.example.playlistmaker.ui.media.favorite_tracks.view_model.FavoriteTracksViewModel
import com.example.playlistmaker.ui.media.playlists.view_model.PlaylistsViewModel
import com.example.playlistmaker.ui.player.view_model.PlayerViewModel
import com.example.playlistmaker.ui.search.view_model.SearchViewModel
import com.example.playlistmaker.ui.settings.view_model.SettingsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel<SearchViewModel> {
        SearchViewModel(searchInteractor = get())
    }

    viewModel<PlayerViewModel> {
        PlayerViewModel(playerInteractor = get(), androidApplication())
    }

    viewModel<SettingsViewModel> {
        SettingsViewModel(settingsInteractor = get(),sharingInteractor = get() )
    }

    viewModel<FavoriteTracksViewModel> {
        FavoriteTracksViewModel()
    }

    viewModel<PlaylistsViewModel> {
        PlaylistsViewModel()
    }
}