package com.example.playlistmaker.ui.settings.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.R
import com.example.playlistmaker.data.setting.App
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import com.example.playlistmaker.domain.sharing.model.IntentWorkPlace
import com.example.playlistmaker.data.setting.ThemeDataSource
import com.example.playlistmaker.ui.settings.view_model.SettingsViewModel

class SettingsActivity : ComponentActivity() {
    //AppCompatActivity()
    private var binding: ActivitySettingsBinding? = null
    private lateinit var viewModelSettings: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelSettings = ViewModelProvider(this, SettingsViewModel.getViewModelFactory())[SettingsViewModel::class.java]

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