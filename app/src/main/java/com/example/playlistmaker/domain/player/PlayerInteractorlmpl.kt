package com.example.playlistmaker.domain.player

import android.media.MediaPlayer
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityMediaBinding

class PlayerInteractorlmpl : PlayerInteractor {
    private var _playerState: State? = null

    override var playerState: State?
        get() = _playerState
        set(value) { _playerState = value }

    override fun pausePlayer(mediaPlayer: MediaPlayer, binding: ActivityMediaBinding) {
        mediaPlayer.pause()
        binding.ibPlay.setImageResource(R.drawable.button_play)
        playerState = State.PAUSED
    }

    override fun startPlayer(mediaPlayer: MediaPlayer, binding: ActivityMediaBinding){
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            binding.ibPlay.setImageResource(R.drawable.button_pause)
            playerState = State.PLAYING
        }
    }

    override fun playbackControl(mediaPlayer: MediaPlayer, binding: ActivityMediaBinding) {
        when(playerState) {
            State.PLAYING -> pausePlayer(mediaPlayer,binding)
            State.PREPARED, State.PAUSED -> startPlayer(mediaPlayer,binding)
            State.DEFAULT -> {}
            else -> {}
        }
    }
}