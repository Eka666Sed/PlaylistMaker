package com.example.playlistmaker.creator

import android.content.Context
import com.example.playlistmaker.data.sharing.ExternalNavigator
import com.example.playlistmaker.data.sharing.impl.ExternalNavigatorImpl
import com.example.playlistmaker.domain.sharing.SharingInteractor
import com.example.playlistmaker.domain.sharing.impl.SharingInteractorImpl

object SharingCreator {
    private var sharingInteractor: SharingInteractor? = null
    private var externalNavigator: ExternalNavigator? = null

    fun createInteractor(context: Context): SharingInteractor {
        val externalNavigator = this.externalNavigator ?: createExternalNavigator(context)
        return sharingInteractor ?: SharingInteractorImpl(externalNavigator).apply {
            sharingInteractor = this
        }
    }

    private fun createExternalNavigator(context: Context): ExternalNavigator {
        return externalNavigator ?: ExternalNavigatorImpl(context).apply {
            externalNavigator = this
        }
    }
}