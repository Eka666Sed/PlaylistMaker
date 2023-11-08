package com.example.playlistmaker.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.R
import com.example.playlistmaker.app.App
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import com.example.playlistmaker.util.ObjectCollection.intentView

class SettingsActivity : AppCompatActivity() {
    private var binding: ActivitySettingsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        checkTheme()
        setUpToolbar()
        onClickListenerButton()
    }

    private fun setUpToolbar() = binding?.toolbarSettings?.setNavigationOnClickListener { onBackPressed() }

    private fun toggleTheme(checked:Boolean) {
        (applicationContext as App).apply {
            switchTheme(checked)
            saveData(checked)
        }
    }
    private fun checkTheme(){
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> binding?.themeSwither?.isChecked = true
            AppCompatDelegate.MODE_NIGHT_NO -> binding?.themeSwither?.isChecked = false
        }
    }

    private fun onClickListenerButton() {
        binding?.buttonShareApp?.setOnClickListener {
            intentView.shareText(this,getString(R.string.web_document))
        }
        binding?.buttonWriteSupport?.setOnClickListener {
            intentView.openEmailApp(this)
        }
        binding?.buttonUserAgreement?.setOnClickListener {
            intentView.parseWeb(this)
        }
        binding?.themeSwither?.setOnCheckedChangeListener { switcher, checked ->
            toggleTheme(checked)
        }
    }
}