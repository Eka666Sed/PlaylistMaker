package com.example.playlistmaker.domain.main

import androidx.lifecycle.LiveData

interface NavigationInteractor {
    val isBottomNavigationVisible: LiveData<Boolean>

    fun setBottomNavigationVisibility(isVisible: Boolean)
}