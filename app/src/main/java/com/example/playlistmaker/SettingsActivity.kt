package com.example.playlistmaker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class SettingsActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setUpToolbar()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setUpToolbar() {
        toolbar = findViewById(R.id.toolbarSettings)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}