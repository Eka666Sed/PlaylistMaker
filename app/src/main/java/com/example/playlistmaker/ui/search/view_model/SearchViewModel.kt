package com.example.playlistmaker.ui.search.view_model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.search.SearchInteractor
import com.example.playlistmaker.domain.utils.Resource
import com.example.playlistmaker.ui.search.SearchScreenEvent
import com.example.playlistmaker.ui.search.SearchScreenState
import com.example.playlistmaker.ui.util.SingleLiveEvent
import com.example.playlistmaker.ui.util.debounce
import kotlinx.coroutines.launch

class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private val searchTrackDebounce = debounce<Unit>(SEARCH_DEBOUNCE_DELAY, viewModelScope){
        _ -> searchTracks()
    }

    private val onTrackClickDebounce = debounce<Track>(
        CLICK_DEBOUNCE_DELAY,
        viewModelScope,
        false
    ) {track ->
        searchInteractor.addTrackToHistory(track)
        searchInteractor.saveTrackForPlaying(track)
        tracksHistory = searchInteractor.getTracksHistory()
        event.postValue(SearchScreenEvent.OpenPlayerScreen)
    }

    private var searchRequest: String = ""
    private var tracksHistory = listOf<Track>()

    private val _state = MutableLiveData<SearchScreenState>()
    val state: LiveData<SearchScreenState> = _state

    val event = SingleLiveEvent<SearchScreenEvent>()

    init {
        getTracksHistory()
    }

    fun onSearchRequestChanged(newSearchRequest: String) {
        searchRequest = newSearchRequest
        if (newSearchRequest.isNotEmpty()) {
            _state.value = getCurrentScreenState().copy(
                clearButtonVisible = searchRequest.isNotEmpty(),
                tracksHistoryVisible = false
            )
            searchTrackDebounce(Unit)
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

    fun onMessageButtonClicked() = searchTrackDebounce(Unit)

    fun onEnterClicked() {
        if (searchRequest.isNotEmpty())
            searchTrackDebounce(Unit)
    }

    fun onTrackClicked(track: Track) = onTrackClickDebounce(track)

    private fun getTracksHistory() {
        tracksHistory = searchInteractor.getTracksHistory()
        _state.value = SearchScreenState(tracksHistory = tracksHistory)
    }

    private fun searchTracks() {
        _state.value = getCurrentScreenState().copy(progressVisible = true)
        viewModelScope.launch {
            searchInteractor.searchTracks(searchRequest)
                .collect { tracks ->
                if (tracks.data.isNullOrEmpty() &&
                    tracks.message == "Проверьте подключение к интернету"
                )
                    handleFailure()
                else
                    handleSuccess(tracks)
            }
        }
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