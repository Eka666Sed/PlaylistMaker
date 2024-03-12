package com.example.playlistmaker.di

import com.example.playlistmaker.ui.main.view_model.MainViewModel
import com.example.playlistmaker.ui.media.favorite_tracks.view_model.FavoriteTracksViewModel
import com.example.playlistmaker.ui.media.create_playlist.view_model.CreatePlaylistViewModel
import com.example.playlistmaker.ui.media.playlists.view_model.PlaylistsViewModel
import com.example.playlistmaker.ui.player.fragment.view_model.PlayerViewModel
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
        PlayerViewModel(
            favoriteTracksInteractor = get(),
            playlistInteractor = get(),
            playerInteractor = get(),
            application = androidApplication())
    }

    viewModel<SettingsViewModel> {
        SettingsViewModel(settingsInteractor = get(),sharingInteractor = get() )
    }

    viewModel<FavoriteTracksViewModel> {
        FavoriteTracksViewModel(favoriteTracksInteractor = get())
    }

    viewModel<PlaylistsViewModel> {
        PlaylistsViewModel(
            navigationInteractor = get(),
            playlistInteractor = get()
        )
    }
    viewModel<CreatePlaylistViewModel> {
        CreatePlaylistViewModel(navigationInteractor = get(), playlistInteractor = get())
    }

    viewModel<MainViewModel> {
        MainViewModel(navigationInteractor = get())
    }
}