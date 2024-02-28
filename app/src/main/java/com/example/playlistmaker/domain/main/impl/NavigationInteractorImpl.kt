package com.example.playlistmaker.domain.main.impl

import androidx.lifecycle.LiveData
import com.example.playlistmaker.data.main.NavigationRepository
import com.example.playlistmaker.domain.main.NavigationInteractor

class NavigationInteractorImpl(
    private val navigationRepository: NavigationRepository
) : NavigationInteractor {

    override val isBottomNavigationVisible: LiveData<Boolean> =
        navigationRepository.isBottomNavigationVisible

    override fun setBottomNavigationVisibility(isVisible: Boolean) {
        navigationRepository.setBottomNavigationVisibility(isVisible)
    }
}