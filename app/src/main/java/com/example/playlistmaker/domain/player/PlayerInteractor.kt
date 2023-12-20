package com.example.playlistmaker.domain.player

import android.media.MediaPlayer
import com.example.playlistmaker.databinding.ActivityPlayerBinding
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.player.model.PlayerState

interface PlayerInteractor {
    var playerState: PlayerState?

    fun pausePlayer(mediaPlayer: MediaPlayer, binding: ActivityPlayerBinding)

    fun startPlayer(mediaPlayer: MediaPlayer, binding: ActivityPlayerBinding)

    fun playbackControl(mediaPlayer: MediaPlayer, binding: ActivityPlayerBinding)

    fun getTrackForPlaying(): Track?
}