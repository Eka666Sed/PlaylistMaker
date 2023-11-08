package com.example.playlistmaker.presentation

import android.media.MediaPlayer
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityMediaBinding
import com.example.playlistmaker.util.State

object MediaPlayer {
    var playerState: State? = null
    fun pausePlayer(mediaPlayer: MediaPlayer, binding: ActivityMediaBinding) {
        mediaPlayer.pause()
        binding.ibPlay.setImageResource(R.drawable.button_play)
        playerState = State.PAUSED
    }

    fun startPlayer(mediaPlayer: MediaPlayer, binding: ActivityMediaBinding){
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            binding.ibPlay.setImageResource(R.drawable.button_pause)
            playerState = State.PLAYING
        }
    }

    fun playbackControl(mediaPlayer: MediaPlayer, binding: ActivityMediaBinding) {
        when(playerState) {
            State.PLAYING -> pausePlayer(mediaPlayer,binding)
            State.PREPARED, State.PAUSED -> startPlayer(mediaPlayer,binding)
            State.DEFAULT -> {}
            else -> {}
        }
    }
}