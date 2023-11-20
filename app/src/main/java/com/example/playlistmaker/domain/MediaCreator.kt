package com.example.playlistmaker.domain

import com.example.playlistmaker.domain.utils.PlayerInteractor
import com.example.playlistmaker.domain.utils.PlayerInteractorlmpl

object MediaCreator {
    fun providePlayerInteractor() : PlayerInteractor = PlayerInteractorlmpl()
}