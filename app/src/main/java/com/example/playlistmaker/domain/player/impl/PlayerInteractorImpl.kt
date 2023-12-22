package com.example.playlistmaker.domain.player.impl

import com.example.playlistmaker.data.search.TracksRepository
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.player.PlayerInteractor

class PlayerInteractorImpl(
    private val trackRepository: TracksRepository
) : PlayerInteractor {

    override fun getTrackForPlaying(): Track? = trackRepository.getTrackForPlaying()
}