package com.example.playlistmaker.domain.utils

import android.media.MediaPlayer
import com.example.playlistmaker.databinding.ActivityMediaBinding

interface PlayerInteractor {
    var playerState: State?
    fun pausePlayer(mediaPlayer: MediaPlayer, binding: ActivityMediaBinding)

    fun startPlayer(mediaPlayer: MediaPlayer, binding: ActivityMediaBinding)

    fun playbackControl(mediaPlayer: MediaPlayer, binding: ActivityMediaBinding)
}