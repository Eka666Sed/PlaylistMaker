package com.example.playlistmaker.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.playlistmaker.R
import com.example.playlistmaker.app.App
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import com.example.playlistmaker.presentation.utils.IntentWorkPlace
import com.example.playlistmaker.ui.settings.view_model.SettingsViewModel

class SettingsActivity : AppCompatActivity() {

    private var binding: ActivitySettingsBinding? = null
    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        viewModel = ViewModelProvider(this).get<SettingsViewModel>()
        setUpToolbar()
        setUpButtons()
        setUpThemeSwitch()
    }

    private fun setUpToolbar() = binding?.toolbarSettings?.setNavigationOnClickListener {
        onBackPressed()
    }

    private fun setUpButtons() {
        binding?.apply {
            buttonShareApp.setOnClickListener { viewModel.onShareAppButtonClicked() }
            buttonWriteSupport.setOnClickListener { viewModel.onWriteSupportButtonClicked() }
            buttonUserAgreement.setOnClickListener { viewModel.onUserAgreementsButtonClicked() }
        }
    }

    private fun setUpThemeSwitch() {
        binding?.themeSwither?.apply {
            viewModel.applicationTheme.observe(this@SettingsActivity) {
                isChecked = it
            }
            setOnClickListener { viewModel.onThemeSwitchClicked(this.isChecked) }
        }
    }
}