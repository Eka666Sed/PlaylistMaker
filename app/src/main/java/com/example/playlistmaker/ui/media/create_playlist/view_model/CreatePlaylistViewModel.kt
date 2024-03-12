package com.example.playlistmaker.ui.media.create_playlist.view_model

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.main.NavigationInteractor
import com.example.playlistmaker.domain.playlist.PlaylistInteractor
import com.example.playlistmaker.ui.media.create_playlist.CreatePlaylistEvent
import com.example.playlistmaker.ui.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class CreatePlaylistViewModel(
    private val navigationInteractor: NavigationInteractor,
    private val playlistInteractor: PlaylistInteractor
) : ViewModel() {

    companion object {
        const val KEY_PLAYLIST_COVER_URI = "key_playlist_cover_uri"
    }

    protected var playlistName = ""
    protected var playlistDescription: String? = null

    protected val _playlistCoverUri: MutableLiveData<Uri?> = MutableLiveData()
    val playlistCoverUri: LiveData<Uri?> = _playlistCoverUri

    private val _isButtonCreateEnabled = MutableLiveData<Boolean>(false)
    val isButtonCreateEnabled: LiveData<Boolean> = _isButtonCreateEnabled

    val event = SingleLiveEvent<CreatePlaylistEvent>()

    fun onPlaylistNameChanged(name: String) {
        playlistName = name
        _isButtonCreateEnabled.value = playlistName.isNotEmpty()
    }

    fun onPlaylistDescriptionChanged(description: String) {
        playlistDescription = description
    }

    open fun onBackPressed() {
        if (checkPlaylistCreationIsNotFinished()) {
            event.value = CreatePlaylistEvent.ShowBackConfirmationDialog
        } else {
            navigateBack()
        }
    }

    open fun onCompleteButtonClicked() {
        if (playlistName.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                playlistInteractor.addPlaylist(
                    playlistName,
                    playlistDescription,
                    playlistCoverUri.value
                )
                withContext(Dispatchers.Main) {
                    event.value = CreatePlaylistEvent.SetPlaylistCreatedResult(playlistName)
                    navigateBack()
                }
            }
        }
    }

    fun onBackPressedConfirmed() = navigateBack()

    fun onPlaylistCoverSelected(url: Uri) {
        _playlistCoverUri.value = url
    }

    protected fun navigateBack() {
        event.value = CreatePlaylistEvent.NavigateBack
        navigationInteractor.setBottomNavigationVisibility(true)
    }

    private fun checkPlaylistCreationIsNotFinished(): Boolean {
        return playlistName.isNotEmpty() ||
                !playlistDescription.isNullOrEmpty() ||
                playlistCoverUri.value != null
    }

}