package com.example.playlistmaker.ui.player

import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.player.model.PlayerState

data class PlayerScreenState(
    val playerState: PlayerState = PlayerState.PREPARED,
    val track: Track?=null,
    val trackTime: String = ""
)
