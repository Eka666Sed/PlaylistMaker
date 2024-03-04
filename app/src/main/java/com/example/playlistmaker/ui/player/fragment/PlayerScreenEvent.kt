package com.example.playlistmaker.ui.player.fragment

sealed class PlayerScreenEvent{
    object OpenPlaylistsBottomSheet : PlayerScreenEvent()
    object ClosePlaylistsBottomSheet : PlayerScreenEvent()
    data class ShowTrackAddedMessage(val playlistName: String) : PlayerScreenEvent()
    data class ShowTrackAlreadyInPlaylistMessage(val playlistName: String) : PlayerScreenEvent()
    object NavigateToCreatePlaylistScreen : PlayerScreenEvent()
}
