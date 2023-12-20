package com.example.playlistmaker.ui.search

sealed class SearchScreenEvent{
    object HideKeyboard : SearchScreenEvent()
    object OpenPlayerScreen : SearchScreenEvent()
}
