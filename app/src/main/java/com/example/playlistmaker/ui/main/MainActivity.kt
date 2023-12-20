package com.example.playlistmaker.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playlistmaker.databinding.ActivityMainBinding
import com.example.playlistmaker.ui.settings.SettingsActivity
import com.example.playlistmaker.ui.search.SearchActivity
import com.example.playlistmaker.ui.player.PlayerActivity


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUpButtonListeners()
    }

    private fun setUpButtonListeners() {
        binding?.apply {
            buttonSearch.setOnClickListener {
                startActivity(Intent(this@MainActivity, SearchActivity()::class.java))
            }
            buttonMedia.setOnClickListener {
                startActivity(Intent(this@MainActivity, PlayerActivity()::class.java))
            }
            buttonSettings.setOnClickListener {
                startActivity(Intent(this@MainActivity, SettingsActivity()::class.java))
            }
        }

    }
}