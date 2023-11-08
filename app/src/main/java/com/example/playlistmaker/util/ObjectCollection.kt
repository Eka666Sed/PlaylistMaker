package com.example.playlistmaker.util


import com.example.playlistmaker.data.SharedPreferenceWorkPlace
import com.example.playlistmaker.data.entities.helpers.FormatedData
import com.example.playlistmaker.data.web_action.WebWorkPlace
import com.example.playlistmaker.domain.GetWebDataUseCase
import com.example.playlistmaker.presentation.MediaPlayer
import com.example.playlistmaker.presentation.helpers.ActionsAdapterWorkPlace
import com.example.playlistmaker.presentation.helpers.ImageWorkPlace
import com.example.playlistmaker.presentation.helpers.IntentWorkPlace
import com.example.playlistmaker.presentation.helpers.ViewActionsWorkPlace

object ObjectCollection {
    val adapter = ActionsAdapterWorkPlace
    val web = WebWorkPlace
    val image = ImageWorkPlace
    val view = ViewActionsWorkPlace
    val sharedPreferences = SharedPreferenceWorkPlace
    val formatedData = FormatedData
    val intentView = IntentWorkPlace
    val mediaPlayerAction = MediaPlayer
    val useCase = GetWebDataUseCase
}