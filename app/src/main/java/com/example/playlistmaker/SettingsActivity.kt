package com.example.playlistmaker

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class SettingsActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setUpToolbar()
        val shareButton: Button = findViewById(R.id.buttonShareApp)
        shareButton.setOnClickListener {
            shareText("https://practicum.yandex.ru/android-developer/", this)
        }
        val buttonWriteSupport: Button = findViewById(R.id.buttonWriteSupport)
        buttonWriteSupport.setOnClickListener {
            openEmailApp()
        }
        val buttonUserAgreement: Button = findViewById(R.id.buttonUserAgreement)
        buttonUserAgreement.setOnClickListener {
            parseWeb()
        }
    }

    fun shareText(text: String, activity: Activity) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        activity.startActivity(shareIntent)
    }


    private fun setUpToolbar() {
        toolbar = findViewById(R.id.toolbarSettings)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun openEmailApp() {
        val message = "Спасибо разработчикам и разработчицам за крутое приложение"
        val theme = "Спасибо разработчикам и разработчицам приложения Playlist Maker"
        val shareIntent = Intent(Intent.ACTION_SENDTO)
        shareIntent.data = Uri.parse("mailto:")
        shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("mrs_sedova@mail.ru"))
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, theme)
        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
        startActivity(shareIntent)

        if (shareIntent.resolveActivity(packageManager) != null) {
            startActivity(shareIntent)
        } else {
        }
    }
    fun parseWeb() {
        val url = "https://yandex.ru/legal/practicum_offer/"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

}