package com.example.playlistmaker.data.main

import androidx.lifecycle.LiveData

interface NavigationRepository {
    val isBottomNavigationVisible: LiveData<Boolean>

    fun setBottomNavigationVisibility(isVisible: Boolean)
}