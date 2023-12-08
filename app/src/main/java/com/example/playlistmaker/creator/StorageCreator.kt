package com.example.playlistmaker.creator

import com.example.playlistmaker.data.setting.impl.StorageRepositoryImpl
import com.example.playlistmaker.data.setting.impl.StorageInteractorImpl
import com.example.playlistmaker.domain.settings.model.StorageInteractor

object StorageCreator {
    fun provideStorageInteractor() : StorageInteractor = StorageInteractorImpl(StorageRepositoryImpl())
}