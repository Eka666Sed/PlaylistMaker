package com.example.playlistmaker.ui.media.playlist_info.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.main.NavigationInteractor
import com.example.playlistmaker.domain.model.Playlist
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.playlist.PlaylistInteractor
import com.example.playlistmaker.domain.sharing.SharingInteractor
import com.example.playlistmaker.ui.media.playlist_info.PlaylistInfoScreenEvent
import com.example.playlistmaker.ui.media.playlist_info.PlaylistInfoScreenState
import com.example.playlistmaker.ui.media.playlist_info.menu.PlaylistInfoMenuItem
import com.example.playlistmaker.ui.util.SingleLiveEvent
import com.example.playlistmaker.ui.util.calculateTotalDuration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaylistInfoViewModel(
    private val playlistInteractor: PlaylistInteractor,
    private val navigationInteractor: NavigationInteractor,
    private val sharingInteractor: SharingInteractor,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val KEY_PLAYLIST_ID = "playlist_id"
    }

    private val playlist = MutableStateFlow<Playlist?>(null)
    private val tracks = MutableStateFlow<List<Track>>(listOf())

    private var trackIdForDeletion: Long? = null

    private val _screenState = MutableLiveData<PlaylistInfoScreenState>()
    val screenState: LiveData<PlaylistInfoScreenState> = _screenState

    private val _menuItems = MutableLiveData<List<PlaylistInfoMenuItem>>()
    val menuItems: LiveData<List<PlaylistInfoMenuItem>> = _menuItems

    val event = SingleLiveEvent<PlaylistInfoScreenEvent>()

    init {
        subscribeOnPlaylist()
        subscribeOnScreenState()
        createMenuItems()
    }

    fun onBackPressed() = navigateBack()

    fun onShareButtonClicked() {
        if (tracks.value.isNotEmpty()) {
            playlist.value?.let {
                sharingInteractor.sharePlaylist(it.name, it.description ?: "", tracks.value)
            }
        } else
            event.postValue(PlaylistInfoScreenEvent.ShowNoTracksInPlaylistMessage)
    }

    fun onMenuButtonClicked() = event.postValue(PlaylistInfoScreenEvent.SetMenuVisibility(true))

    fun onDeletePlaylistConfirmed() {
        playlist.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                playlistInteractor.deletePlaylist(it)
                withContext(Dispatchers.Main) {
                    navigateBack()
                }
            }
        }
    }

    fun onTrackClicked(track: Track) {
        playlistInteractor.saveTrackForPlaying(track)
        event.value = PlaylistInfoScreenEvent.OpenPlayerScreen
    }

    fun onTrackLongClicked(trackId: Long) {
        trackIdForDeletion = trackId
        event.postValue(PlaylistInfoScreenEvent.ShowDeleteTrackDialog)
    }

    fun onDeleteTrackConfirmed() {
        playlist.value?.apply {
            trackIdForDeletion?.let { id ->
                viewModelScope.launch(Dispatchers.IO) {
                    playlistInteractor.deleteTrackFromPlaylist(this@apply, id)
                }.invokeOnCompletion { trackIdForDeletion = null }
            }
        }
    }

    fun onDeleteTrackCanceled() {
        trackIdForDeletion = null
    }

    private fun onEditPlaylistClicked() {
        playlist.value?.let { event.postValue(PlaylistInfoScreenEvent.NavigateToEditPlaylist(it)) }
    }

    private fun onDeletePlaylistClicked() {
        event.value = PlaylistInfoScreenEvent.SetMenuVisibility(false)
        playlist.value?.let {
            event.postValue(PlaylistInfoScreenEvent.ShowDeletePlaylistDialog(it.name))
        }
    }

    private fun subscribeOnPlaylist() {
        val playlistId = savedStateHandle.get<String?>(KEY_PLAYLIST_ID)
        playlistId?.let {
            playlistInteractor.getPlaylistById(playlistId).onEach { updatedPlaylist ->
                if (updatedPlaylist.tracksCount != playlist.value?.tracksCount) {
                    getPlaylistTracks(updatedPlaylist.tracksIds)
                }
                playlist.update { updatedPlaylist }
            }.launchIn(viewModelScope)
        }
    }

    private suspend fun getPlaylistTracks(tracksIds: List<Long>) {
        viewModelScope.launch(Dispatchers.IO) {
            tracks.update { playlistInteractor.getPlaylistTracks(tracksIds) }
        }
    }

    private fun subscribeOnScreenState() {
        combine(playlist, tracks) { playlist, tracks ->
            val state = PlaylistInfoScreenState(
                playlistCoverUri = playlist?.coverUri,
                playlistName = playlist?.name ?: "",
                playlistDescription = playlist?.description ?: "",
                playlistDuration = tracks.calculateTotalDuration(),
                tracksCount = tracks.count(),
                tracks = tracks
            )
            _screenState.postValue(state)
        }.launchIn(viewModelScope)
    }

    private fun createMenuItems() {
        _menuItems.value = listOf(
            PlaylistInfoMenuItem.Share { onShareButtonClicked() },
            PlaylistInfoMenuItem.Edit { onEditPlaylistClicked() },
            PlaylistInfoMenuItem.Delete { onDeletePlaylistClicked() }
        )
    }

    private fun navigateBack() {
        event.postValue(PlaylistInfoScreenEvent.NavigateBack)
        navigationInteractor.setBottomNavigationVisibility(true)
    }
}