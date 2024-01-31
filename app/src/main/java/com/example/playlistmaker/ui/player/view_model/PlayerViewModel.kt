package com.example.playlistmaker.ui.player.view_model

import android.app.Application
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.player.PlayerInteractor
import com.example.playlistmaker.domain.player.model.PlayerState
import com.example.playlistmaker.domain.utils.DateFormatter
import com.example.playlistmaker.ui.player.PlayerScreenEvent
import com.example.playlistmaker.ui.player.PlayerScreenState
import com.example.playlistmaker.ui.util.SingleLiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerViewModel(
    playerInteractor: PlayerInteractor,
    application: Application
) : AndroidViewModel(application) {

    companion object {
        private const val TIME_STEP_MILLIS = 300L
    }

    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var trackDurationJob: Job? = null

    private val track = playerInteractor.getTrackForPlaying()

    private val _state = MutableLiveData<PlayerScreenState>()
    val state: LiveData<PlayerScreenState> = _state

    val event = SingleLiveEvent<PlayerScreenEvent>()

    init {
        _state.value = PlayerScreenState(PlayerState.PAUSED, track)
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

    fun onPlayButtonClicked() {
        track?.let {
            when (getCurrentScreenState().playerState) {
                PlayerState.PLAYING -> pausePlayer()
                PlayerState.PREPARED, PlayerState.PAUSED -> startPlayer()
            }
        }
    }

    fun onLikeButtonClicked() {
        event.postValue(PlayerScreenEvent.ShowPlayListCreatedToast)
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
                    val time = DateFormatter.formatMillisToString(mediaPlayer.currentPosition.toLong())
                _state.postValue(PlayerScreenState(PlayerState.PLAYING, track, time))
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

    private fun getCurrentScreenState() = _state.value ?: PlayerScreenState()
}