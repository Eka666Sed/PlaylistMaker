package com.example.playlistmaker.domain.player.impl

import android.media.MediaPlayer
import com.example.playlistmaker.R
import com.example.playlistmaker.data.search.TracksRepository
import com.example.playlistmaker.databinding.ActivityPlayerBinding
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.player.PlayerInteractor
import com.example.playlistmaker.domain.player.model.PlayerState

class PlayerInteractorImpl(
    private val trackRepository: TracksRepository
) : PlayerInteractor {
    private var _playerState: PlayerState? = null

    override var playerState: PlayerState?
        get() = _playerState
        set(value) {
            _playerState = value
        }

    override fun pausePlayer(mediaPlayer: MediaPlayer, binding: ActivityPlayerBinding) {
        mediaPlayer.pause()
        binding.ibPlay.setImageResource(R.drawable.button_play)
        playerState = PlayerState.PAUSED
    }

    override fun startPlayer(mediaPlayer: MediaPlayer, binding: ActivityPlayerBinding) {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            binding.ibPlay.setImageResource(R.drawable.button_pause)
            playerState = PlayerState.PLAYING
        }
    }

    override fun playbackControl(mediaPlayer: MediaPlayer, binding: ActivityPlayerBinding) {
        when (playerState) {
            PlayerState.PLAYING -> pausePlayer(mediaPlayer, binding)
            PlayerState.PREPARED, PlayerState.PAUSED -> startPlayer(mediaPlayer, binding)
            else -> {}
        }
    }

    override fun getTrackForPlaying(): Track? = trackRepository.getTrackForPlaying()
}