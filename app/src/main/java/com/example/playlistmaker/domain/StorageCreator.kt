package com.example.playlistmaker.domain

import com.example.playlistmaker.data.StorageRepositoryImpl
import com.example.playlistmaker.domain.impl.StorageInteractorImpl
import com.example.playlistmaker.domain.storage.StorageInteractor

object StorageCreator {
    fun provideStorageInteractor() : StorageInteractor = StorageInteractorImpl(StorageRepositoryImpl())
}