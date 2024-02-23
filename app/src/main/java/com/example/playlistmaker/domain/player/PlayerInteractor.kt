package com.example.playlistmaker.domain.player

import com.example.playlistmaker.domain.model.Track

interface PlayerInteractor {

    fun getTrackForPlaying(): Track?
}