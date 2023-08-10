package com.example.playlistmaker

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar

class SettingsActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var shareButton: Button
    private lateinit var buttonWriteSupport: Button
    private lateinit var buttonUserAgreement: Button
    private lateinit var themeSwitch: Switch


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        shareButton = findViewById(R.id.buttonShareApp)
        buttonWriteSupport = findViewById(R.id.buttonWriteSupport)
        buttonUserAgreement = findViewById(R.id.buttonUserAgreement)
        themeSwitch = findViewById(R.id.switchTheme)
        val isDarkMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        themeSwitch.isChecked = isDarkMode
        setUpToolbar()
        onClickListenerButton()

    }
    private fun setUpToolbar() {
        toolbar = findViewById(R.id.toolbarSettings)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun shareText(text: String, activity: Activity) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = getString(R.string.type_share_text)
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        activity.startActivity(shareIntent)
    }
    private fun toggleTheme(isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            shareButton.setBackgroundColor(Color.BLACK)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
    private fun openEmailApp() {
        val message = getString(R.string.email_message)
        val theme = getString(R.string.email_theme)
        val shareIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(getString(R.string.url_mail))
            putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.my_email)))
            putExtra(Intent.EXTRA_SUBJECT, theme)
            putExtra(Intent.EXTRA_TEXT, message)
        }
        startActivity(shareIntent)
    }

    private fun parseWeb() {
        val url = getString(R.string.web_url)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun onClickListenerButton(){
        shareButton.setOnClickListener {
            shareText(getString(R.string.web_document), this)
        }
        buttonWriteSupport.setOnClickListener {
            openEmailApp()
        }
        buttonUserAgreement.setOnClickListener {
            parseWeb()
        }
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            toggleTheme(isChecked)
        }
    }
}