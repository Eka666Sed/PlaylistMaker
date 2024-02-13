package com.example.playlistmaker.ui.media.favorite_tracks.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.favorite_tracks.FavoriteTracksInteractor
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.ui.media.favorite_tracks.FavoriteTracksScreenEvent
import com.example.playlistmaker.ui.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteTracksViewModel(
    private val favoriteTracksInteractor: FavoriteTracksInteractor
) : ViewModel() {

    private val _tracks = MutableLiveData<List<Track>>(listOf())
    val tracks: LiveData<List<Track>> = _tracks

    val event = SingleLiveEvent<FavoriteTracksScreenEvent>()

    init {
        subscribeOnFavoriteTracks()
    }

    fun onTrackClicked(track: Track) {
        favoriteTracksInteractor.saveTrackForPlaying(track)
        event.value = FavoriteTracksScreenEvent.OpenPlayerScreen
    }

    private fun subscribeOnFavoriteTracks() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteTracksInteractor.getFavoriteTracks().collect { _tracks.postValue(it) }
        }
    }
}