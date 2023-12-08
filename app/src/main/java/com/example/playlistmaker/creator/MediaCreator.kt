package com.example.playlistmaker.creator

import com.example.playlistmaker.domain.player.PlayerInteractor
import com.example.playlistmaker.domain.player.PlayerInteractorlmpl

object MediaCreator {
    fun providePlayerInteractor() : PlayerInteractor = PlayerInteractorlmpl()
}