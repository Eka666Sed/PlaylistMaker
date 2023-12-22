package com.example.playlistmaker.domain.player

import com.example.playlistmaker.domain.models.Track

interface PlayerInteractor {

    fun getTrackForPlaying(): Track?
}