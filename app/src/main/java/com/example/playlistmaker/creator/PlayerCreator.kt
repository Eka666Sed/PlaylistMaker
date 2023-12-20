package com.example.playlistmaker.creator

import android.content.Context
import com.example.playlistmaker.domain.player.PlayerInteractor
import com.example.playlistmaker.domain.player.impl.PlayerInteractorImpl

object PlayerCreator {

    private var playerInteractor: PlayerInteractor? = null

    fun providePlayerInteractor(context: Context): PlayerInteractor {
        val trackRepository = TrackRepositoryCreator.createTracksRepository(context)
        return playerInteractor ?: PlayerInteractorImpl(trackRepository).apply {
            playerInteractor = this
        }
    }
}