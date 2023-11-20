package com.example.playlistmaker.presentation.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playlistmaker.databinding.ActivityMainBinding
import com.example.playlistmaker.presentation.ui.media.MediaActivity
import com.example.playlistmaker.presentation.ui.settings.SettingsActivity
import com.example.playlistmaker.presentation.ui.tracks.SearchActivity


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUpButtonListeners()
    }

    private fun setUpButtonListeners() {

        binding?.buttonSearch?.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchActivity()::class.java))
        }
        binding?.buttonMedia?.setOnClickListener {
            startActivity(Intent(this@MainActivity, MediaActivity()::class.java))
        }
        binding?.buttonSettings?.setOnClickListener {
            startActivity(Intent(this@MainActivity, SettingsActivity()::class.java))
        }
    }
}