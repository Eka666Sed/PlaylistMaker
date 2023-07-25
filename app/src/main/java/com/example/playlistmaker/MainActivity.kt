package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var buttonSearch: Button
    private lateinit var buttonMedia: Button
    private lateinit var buttonSettings: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonSearch = findViewById(R.id.buttonSearch)
        buttonMedia = findViewById(R.id.buttonMedia)
        buttonSettings = findViewById(R.id.buttonSettings)

        setUpButtonSearch()
        setUpButtonMedia()
        setUpButtonSettings()
    }
    private fun setUpButtonSearch() {
        buttonSearch.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                showToast(buttonSearch.text.toString())
            }
        })
        buttonSearch.setOnClickListener { showToast(buttonSearch.text.toString()) }
    }

    private fun setUpButtonMedia() {
        buttonMedia.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                showToast(buttonMedia.text.toString())
            }
        })
        buttonMedia.setOnClickListener { showToast(buttonMedia.text.toString()) }
    }

    private fun setUpButtonSettings() {
        buttonSettings.setOnClickListener { showToast(buttonSettings.text.toString()) }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}