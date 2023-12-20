package com.example.playlistmaker.creator

import com.example.playlistmaker.data.search.TracksRepositoryImpl
import com.example.playlistmaker.data.player.RetrofitNetworkClient
import com.example.playlistmaker.domain.search.TracksInteractor
import com.example.playlistmaker.domain.search.TracksRepository
import com.example.playlistmaker.data.search.TracksInteractorImpl

object WebCreator {
    private fun getTracksRepository(): TracksRepository = TracksRepositoryImpl(RetrofitNetworkClient())
    fun provideTracksInteractor(): TracksInteractor = TracksInteractorImpl(getTracksRepository())
}