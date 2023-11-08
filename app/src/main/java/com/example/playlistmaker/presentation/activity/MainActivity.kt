package com.example.playlistmaker.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playlistmaker.databinding.ActivityMainBinding
import com.example.playlistmaker.util.ObjectCollection

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
            ObjectCollection.intentView.navigateToActivity(this, SearchActivity())
        }
        binding?.buttonMedia?.setOnClickListener {
            ObjectCollection.intentView.navigateToActivity(this, MediaActivity())
        }
        binding?.buttonSettings?.setOnClickListener {
            ObjectCollection.intentView.navigateToActivity(this, SettingsActivity())
        }
    }
}