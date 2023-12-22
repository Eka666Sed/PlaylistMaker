package com.example.playlistmaker.data.sharing

interface ExternalNavigator {
    fun shareLink(url: String)
    fun openUserAgreement(url: String)
    fun openEmail(email: String)
}