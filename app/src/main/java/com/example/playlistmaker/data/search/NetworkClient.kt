package com.example.playlistmaker.data.search

import com.example.playlistmaker.data.player.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}