package com.example.playlistmaker.data.sharing

import com.example.playlistmaker.domain.model.Track

interface ExternalNavigator {
    fun shareLink(url: String)
    fun openUserAgreement(url: String)
    fun openEmail(email: String)
    fun sharePlaylist(name: String, description: String, tracks: List<Track>)
}