package com.example.playlistmaker.presentation.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.app.App
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import com.example.playlistmaker.presentation.utils.IntentWorkPlace
import com.example.playlistmaker.app.utils.ThemeDataSource

class SettingsActivity : AppCompatActivity() {
    private var binding: ActivitySettingsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ThemeDataSource.checkTheme(binding!!)
        setUpToolbar()
        onClickListenerButton()
    }

    private fun setUpToolbar() = binding?.toolbarSettings?.setNavigationOnClickListener { onBackPressed() }

    private fun toggleTheme(checked:Boolean) {
        (applicationContext as App).apply {
            ThemeDataSource.switchTheme(checked)
            ThemeDataSource.saveData(checked, this)
        }
    }

    private fun onClickListenerButton() {
        binding?.buttonShareApp?.setOnClickListener {
            IntentWorkPlace.shareText(this,getString(R.string.web_document))
        }
        binding?.buttonWriteSupport?.setOnClickListener {
            IntentWorkPlace.openEmailApp(this)
        }
        binding?.buttonUserAgreement?.setOnClickListener {
            IntentWorkPlace.parseWeb(this)
        }
        binding?.themeSwither?.setOnCheckedChangeListener { switcher, checked ->
            toggleTheme(checked)
        }
    }
}