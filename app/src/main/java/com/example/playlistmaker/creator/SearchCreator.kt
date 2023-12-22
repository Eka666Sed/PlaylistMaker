package com.example.playlistmaker.creator

import android.content.Context
import com.example.playlistmaker.domain.search.SearchInteractor
import com.example.playlistmaker.domain.search.impl.SearchInteractorImpl

object SearchCreator {
    private var searchInteractor: SearchInteractorImpl? = null

    fun createInteractor(context: Context): SearchInteractor {
        val trackRepository = TrackRepositoryCreator.createTracksRepository(context)
        return searchInteractor ?: SearchInteractorImpl(trackRepository).apply {
            searchInteractor = this
        }
    }
}