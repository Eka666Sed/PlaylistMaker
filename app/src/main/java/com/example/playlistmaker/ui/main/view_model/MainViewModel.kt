package com.example.playlistmaker.ui.main.view_model

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.main.NavigationInteractor

class MainViewModel(navigationInteractor: NavigationInteractor) : ViewModel() {

    val isBottomNavigationVisible = navigationInteractor.isBottomNavigationVisible
}