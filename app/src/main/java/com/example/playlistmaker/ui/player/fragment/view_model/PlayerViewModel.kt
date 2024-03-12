package com.example.playlistmaker.ui.player.fragment.view_model

import android.app.Application
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.favorite_tracks.FavoriteTracksInteractor
import com.example.playlistmaker.domain.model.Playlist
import com.example.playlistmaker.domain.player.PlayerInteractor
import com.example.playlistmaker.domain.player.model.PlayerState
import com.example.playlistmaker.domain.playlist.PlaylistInteractor
import com.example.playlistmaker.domain.utils.DateFormatter
import com.example.playlistmaker.ui.player.fragment.PlayerScreenEvent
import com.example.playlistmaker.ui.player.fragment.PlayerScreenState
import com.example.playlistmaker.ui.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlayerViewModel(
    private val favoriteTracksInteractor: FavoriteTracksInteractor,
    private val playlistInteractor: PlaylistInteractor,
    playerInteractor: PlayerInteractor,
    application: Application
) : AndroidViewModel(application) {

    companion object {
        private const val TIME_STEP_MILLIS = 300L
    }

    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var trackDurationJob: Job? = null

    private var track = playerInteractor.getTrackForPlaying()

    private val _state = MutableLiveData<PlayerScreenState>()
    val state: LiveData<PlayerScreenState> = _state

    private val _playlists = MutableLiveData<List<Playlist>>(listOf())
    val playlists: LiveData<List<Playlist>> = _playlists

    val event = SingleLiveEvent<PlayerScreenEvent>()

    init {
        _state.value = PlayerScreenState(PlayerState.PAUSED, track)
        subscribeOnFavoriteTracks()
        subscribeOnPlaylists()
        initPlayer()
    }

    override fun onCleared() {
        mediaPlayer.release()
        super.onCleared()
    }

    fun onPause() = pausePlayer()

    fun onStop() {
        if (getCurrentScreenState().playerState != PlayerState.PAUSED) {
            trackDurationJob?.cancel()
            mediaPlayer.release()
        }
    }

    fun onAddButtonClicked() {
        event.value = PlayerScreenEvent.OpenPlaylistsBottomSheet
    }

    fun onPlayButtonClicked() {
        track?.let {
            when (getCurrentScreenState().playerState) {
                PlayerState.PLAYING -> pausePlayer()
                PlayerState.PREPARED, PlayerState.PAUSED -> startPlayer()
            }
        }
    }

    fun onLikeButtonClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            track?.apply {
                if (isFavorite) {
                    favoriteTracksInteractor.deleteFromFavorites(this)
                } else favoriteTracksInteractor.addToFavorites(this)
            }
        }
    }

    fun onCreatePlaylistButtonClicked() {
        event.postValue(PlayerScreenEvent.NavigateToCreatePlaylistScreen)
    }

    fun onPlaylistClicked(playlist: Playlist) {
        track?.let {
            if (it.id !in playlist.tracksIds.toSet()) {
                viewModelScope.launch(Dispatchers.IO) {
                    playlistInteractor.addTrackToPlaylist(playlist, it)
                    withContext(Dispatchers.Main) {
                        event.value = PlayerScreenEvent.ClosePlaylistsBottomSheet
                        event.value = PlayerScreenEvent.ShowTrackAddedMessage(playlist.name)
                    }
                }
            } else {
                event.value = PlayerScreenEvent.ShowTrackAlreadyInPlaylistMessage(playlist.name)
            }
        }
    }

    private fun subscribeOnPlaylists() {
        viewModelScope.launch(Dispatchers.IO) {
            playlistInteractor.getPlaylists().collect { _playlists.postValue(it) }
        }
    }

    private fun pausePlayer() {
        if (getCurrentScreenState().playerState == PlayerState.PLAYING) {
            mediaPlayer.pause()
            _state.value = getCurrentScreenState().copy(playerState = PlayerState.PAUSED)
        }
    }

    private fun startPlayer() {
        if (getCurrentScreenState().playerState != PlayerState.PLAYING) {
            mediaPlayer.start()
           trackDurationJob?.cancel()
            trackDurationJob = viewModelScope.launch {
                while (mediaPlayer.isPlaying){
                    val time =
                        DateFormatter.formatMillisToString(mediaPlayer.currentPosition.toLong())
                    _state.postValue(
                        getCurrentScreenState().copy(
                            playerState = PlayerState.PLAYING,
                            track = track,
                            trackTime = time
                        )
                    )
                    delay(TIME_STEP_MILLIS)
                }
            }
        }
    }

    private fun initPlayer() {
        track?.let {
            mediaPlayer.apply {
                setDataSource(getApplication(), Uri.parse(it.previewUrl))
                prepareAsync()
                setOnPreparedListener {
                    _state.value = getCurrentScreenState().copy(playerState = PlayerState.PREPARED)
                }
                setOnCompletionListener {
                    _state.value =
                        getCurrentScreenState().copy(
                            playerState = PlayerState.PREPARED,
                            trackTime = ""
                        )
                }
            }
        }
    }

    private fun subscribeOnFavoriteTracks() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteTracksInteractor.getFavoriteTracks().collect { favoriteTracks ->
                track?.let {
                    track = it.copy(isFavorite = it.id in favoriteTracks.map { track ->
                        track.id
                    }.toSet())
                    _state.postValue(
                        getCurrentScreenState().copy(isFavoriteTrack = track?.isFavorite ?: false)
                    )
                }
            }
        }
    }

    private fun getCurrentScreenState() = _state.value ?: PlayerScreenState()
}