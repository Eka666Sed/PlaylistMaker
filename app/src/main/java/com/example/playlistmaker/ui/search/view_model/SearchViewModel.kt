package com.example.playlistmaker.ui.search.view_model

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.search.SearchInteractor
import com.example.playlistmaker.domain.utils.Resource
import com.example.playlistmaker.ui.search.SearchScreenEvent
import com.example.playlistmaker.ui.search.SearchScreenState
import com.example.playlistmaker.ui.util.SingleLiveEvent

class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }


    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { searchTracks() }

    private var isTrackClickAllowed = true

    private var searchRequest: String = ""
    private var tracksHistory = listOf<Track>()

    private val _state = MutableLiveData<SearchScreenState>()
    val state: LiveData<SearchScreenState> = _state

    val event = SingleLiveEvent<SearchScreenEvent>()

    init {
        getTracksHistory()
    }

    override fun onCleared() {
        handler.removeCallbacks(searchRunnable)
        super.onCleared()
    }

    fun onSearchRequestChanged(newSearchRequest: String) {
        searchRequest = newSearchRequest
        handler.removeCallbacks(searchRunnable)
        if (newSearchRequest.isNotEmpty()) {
            _state.value = getCurrentScreenState().copy(
                clearButtonVisible = searchRequest.isNotEmpty(),
                tracksHistoryVisible = false
            )
            handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
        } else {
            _state.value = SearchScreenState(
                tracksHistory = tracksHistory,
                tracksHistoryVisible = tracksHistory.isNotEmpty(),
            )
        }
    }

    fun onSearchFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            _state.value = getCurrentScreenState()
                .copy(tracksHistoryVisible = tracksHistory.isNotEmpty())
        }
    }

    fun onClearHistoryButtonClicked() {
        searchInteractor.clearTrackHistory()
        tracksHistory = searchInteractor.getTracksHistory()
        _state.value = getCurrentScreenState().copy(tracksHistoryVisible = false)
    }

    fun onClearButtonClicked() {
        _state.value = SearchScreenState(
            tracksHistory = tracksHistory,
            tracksHistoryVisible = tracksHistory.isNotEmpty(),
        )
        event.value = SearchScreenEvent.HideKeyboard
        event.value = SearchScreenEvent.ClearSearch
    }

    fun onMessageButtonClicked() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    fun onEnterClicked() {
        if (searchRequest.isNotEmpty())
            handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    fun onTrackClicked(track: Track) {
        clickDebounce()
        searchInteractor.addTrackToHistory(track)
        searchInteractor.saveTrackForPlaying(track)
        tracksHistory = searchInteractor.getTracksHistory()
        event.value = SearchScreenEvent.OpenPlayerScreen
    }

    private fun getTracksHistory() {
        tracksHistory = searchInteractor.getTracksHistory()
        _state.value = SearchScreenState(tracksHistory = tracksHistory)
    }

    private fun searchTracks() {
        _state.value = getCurrentScreenState().copy(progressVisible = true)
        val tracksConsumer = object : SearchInteractor.TracksConsumer {
            override fun consume(tracks: Resource<List<Track>>) {
                if (tracks.data.isNullOrEmpty() &&
                    tracks.message == "Проверьте подключение к интернету"
                )
                    handleFailure()
                else
                    handleSuccess(tracks)
            }
        }
        searchInteractor.searchTracks(searchRequest, tracksConsumer)
    }

    private fun clickDebounce(): Boolean {
        val currentIsClickedAllowed = isTrackClickAllowed
        if (isTrackClickAllowed) {
            isTrackClickAllowed = false
            handler.postDelayed({ isTrackClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return currentIsClickedAllowed
    }

    private fun handleSuccess(response: Resource<List<Track>>) {
        val tracks = response.data ?: listOf()
        _state.postValue(
            SearchScreenState(
                clearButtonVisible = searchRequest.isNotEmpty(),
                tracks = tracks,
                tracksHistory = tracksHistory,
                messageVisible = tracks.isEmpty(),
                noTracksVisible = tracks.isEmpty()
            )
        )
    }

    private fun handleFailure() {
        _state.postValue(
            SearchScreenState(
                clearButtonVisible = searchRequest.isNotEmpty(),
                tracksHistory = tracksHistory,
                messageVisible = true,
                noWebVisible = true
            )
        )
    }

    private fun getCurrentScreenState() = state.value ?: SearchScreenState()
}