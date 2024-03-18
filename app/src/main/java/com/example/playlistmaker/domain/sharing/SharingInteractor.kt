package com.example.playlistmaker.domain.sharing

import com.example.playlistmaker.domain.model.Track

interface SharingInteractor {
    fun shareApp()
    fun openUserAgreement()
    fun openSupport()
    fun sharePlaylist(name: String, description: String, tracks: List<Track>)
}