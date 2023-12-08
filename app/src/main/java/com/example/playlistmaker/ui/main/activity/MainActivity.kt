package com.example.playlistmaker.ui.main.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.databinding.ActivityMainBinding
import com.example.playlistmaker.ui.main.view_model.MainViewModel
import com.example.playlistmaker.ui.media.activity.MediaActivity
import com.example.playlistmaker.ui.settings.activity.SettingsActivity
import com.example.playlistmaker.ui.search.activity.SearchActivity
import com.example.playlistmaker.ui.settings.view_model.SettingsViewModel


class MainActivity : ComponentActivity() {
//AppCompatActivity
    private var binding: ActivityMainBinding? = null
    private lateinit var viewModelMain: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelMain = ViewModelProvider(this, MainViewModel.getViewModelFactory())[MainViewModel::class.java]

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