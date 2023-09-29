package com.example.playlistmaker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var shareButton: Button
    private lateinit var buttonWriteSupport: Button
    private lateinit var buttonUserAgreement: Button

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var themeSwitch: SwitchMaterial


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        shareButton = findViewById(R.id.buttonShareApp)
        buttonWriteSupport = findViewById(R.id.buttonWriteSupport)
        buttonUserAgreement = findViewById(R.id.buttonUserAgreement)
        themeSwitch = findViewById(R.id.themeSwither)
        checkTheme()
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

    private fun toggleTheme(checked:Boolean) {
        (applicationContext as App).apply {
            switchTheme(checked)
            saveData(checked)
        }
    }
    private fun checkTheme(){
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> themeSwitch.isChecked = true
            AppCompatDelegate.MODE_NIGHT_NO -> themeSwitch.isChecked = false
        }
    }

    private fun openEmailApp() {
        val message = getString(R.string.email_message)
        val theme = getString(R.string.email_theme)
        Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(getString(R.string.url_mail))
            putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.my_email)))
            putExtra(Intent.EXTRA_SUBJECT, theme)
            putExtra(Intent.EXTRA_TEXT, message)
            startActivity(this)
        }
    }

    private fun parseWeb() {
        val url = getString(R.string.web_url)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun onClickListenerButton() {
        shareButton.setOnClickListener {
            shareText(getString(R.string.web_document), this)
        }
        buttonWriteSupport.setOnClickListener {
            openEmailApp()
        }
        buttonUserAgreement.setOnClickListener {
            parseWeb()
        }
        themeSwitch.setOnCheckedChangeListener { switcher, checked ->
            toggleTheme(checked)
        }
    }
}