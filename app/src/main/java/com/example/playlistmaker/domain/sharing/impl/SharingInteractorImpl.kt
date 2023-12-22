package com.example.playlistmaker.domain.sharing.impl

import com.example.playlistmaker.data.sharing.ExternalNavigator
import com.example.playlistmaker.domain.sharing.SharingInteractor

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator
) : SharingInteractor {

    companion object{
        private const val SHARE_APP_URL = "https://practicum.yandex.ru/android-developer/"
        private const val USER_AGREEMENT_URL = "https://yandex.ru/legal/practicum_offer/"
        private const val EMAIL_ADDRESS = "mrs_sedova@mail.ru"
    }

    override fun shareApp() = externalNavigator.shareLink(SHARE_APP_URL)

    override fun openUserAgreement() = externalNavigator.openUserAgreement(USER_AGREEMENT_URL)

    override fun openSupport() = externalNavigator.openEmail(EMAIL_ADDRESS)
}