package com.example.playlistmaker.ui.media.new_playlist.view_model

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.main.NavigationInteractor
import com.example.playlistmaker.domain.playlist.PlaylistInteractor
import com.example.playlistmaker.ui.media.new_playlist.NewPlaylistEvent
import com.example.playlistmaker.ui.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewPlaylistViewModel(
    private val navigationInteractor: NavigationInteractor,
    private val playlistInteractor: PlaylistInteractor
) : ViewModel() {

    private var playlistName = ""
    private var playlistDescription: String? = null
    private var playlistCoverUri: Uri? = null

    private val _isButtonCreateEnabled = MutableLiveData<Boolean>(false)
    val isButtonCreateEnabled: LiveData<Boolean> = _isButtonCreateEnabled

    val event = SingleLiveEvent<NewPlaylistEvent>()

    fun onBackPressed() {
        if (checkPlaylistCreationIsNotFinished()) {
            event.value = NewPlaylistEvent.ShowBackConfirmationDialog
        } else {
            navigateBack()
        }
    }

    fun onPlaylistNameChanged(name: String) {
        playlistName = name
        _isButtonCreateEnabled.value = playlistName.isNotEmpty()
    }

    fun onPlaylistDescriptionChanged(description: String) {
        playlistDescription = description
    }

    fun onCreatePlaylistClicked() {
        if (playlistName.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                playlistInteractor.addPlaylist(playlistName, playlistDescription, playlistCoverUri)
                withContext(Dispatchers.Main) {
                    event.value = NewPlaylistEvent.SetPlaylistCreatedResult(playlistName)
                    navigateBack()
                }
            }
        }
    }

    fun onBackPressedConfirmed() = navigateBack()

    fun onPlaylistCoverSelected(url: Uri) {
        playlistCoverUri = url
    }

    private fun checkPlaylistCreationIsNotFinished(): Boolean {
        return playlistName.isNotEmpty() ||
                !playlistDescription.isNullOrEmpty() ||
                playlistCoverUri != null
    }

    private fun navigateBack() {
        event.value = NewPlaylistEvent.NavigateBack
        navigationInteractor.setBottomNavigationVisibility(true)
    }
}