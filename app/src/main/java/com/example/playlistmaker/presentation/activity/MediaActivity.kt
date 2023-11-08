package com.example.playlistmaker.presentation.activity

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.data.DataSource
import com.example.playlistmaker.data.SharedPreferenceConverter
import com.example.playlistmaker.databinding.ActivityMediaBinding
import com.example.playlistmaker.presentation.MediaPlayer.playerState
import com.example.playlistmaker.util.Constant
import com.example.playlistmaker.util.ObjectCollection
import com.example.playlistmaker.util.ObjectCollection.mediaPlayerAction
import com.example.playlistmaker.util.State
import java.text.SimpleDateFormat
import java.util.Locale

class MediaActivity : AppCompatActivity() {
    private var binding: ActivityMediaBinding? = null
    private var mediaPlayer: MediaPlayer? = null
    private var mediaUri: Uri? = null
    private val handler = Handler()
    private var progressRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.tvTimeTrack?.text = START_TRACK_VALUE
        binding?.toolbarMedia?.setNavigationOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
                mediaPlayerAction.playerState = State.PAUSED
            }
            finish()
        }
        val itemJson = intent.getStringExtra(Constant.KEY)
        itemJson?.let { setDataForView(it) }
        binding?.ibLike?.setOnClickListener {
            binding?.ibLike?.setImageResource(R.drawable.like)
            Toast.makeText(this, getString(R.string.playlist_create), Toast.LENGTH_LONG).show()
        }
        progressRunnable = object : Runnable {
            override fun run() {
                val currentPosition = SimpleDateFormat(PATTERN, Locale.getDefault()).format(mediaPlayer?.currentPosition)
                if (mediaPlayer?.isPlaying == true) {
                    binding?.tvTimeTrack?.text = currentPosition
                    handler.postDelayed(this, TIME_VALUE_STEP)
                }
                else { ObjectCollection.mediaPlayerAction.pausePlayer(mediaPlayer!!,binding!!) }
            }
        }
        mediaPlayer?.setOnCompletionListener {
            binding?.tvTimeTrack?.text = START_TRACK_VALUE
            mediaPlayerAction.pausePlayer(mediaPlayer!!,binding!!)
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.ibPlay?.setOnClickListener {
            ObjectCollection.mediaPlayerAction.playbackControl(mediaPlayer!!,binding!!)
            handler.post(progressRunnable!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(progressRunnable!!)
        exitForTrack()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.let { player ->
            binding?.let { bind ->
                ObjectCollection.mediaPlayerAction.pausePlayer(player, bind)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (mediaPlayerAction.playerState != State.PAUSED) {
            handler.removeCallbacks(progressRunnable!!)
            exitForTrack()
        }
    }

    private fun setDataForView(data: String) {
        val item = SharedPreferenceConverter.createTrackFromJson(data)
        mediaUri = Uri.parse(item.previewUrl)
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource(applicationContext, mediaUri!!)
        mediaPlayer?.prepareAsync()
        mediaPlayer?.setOnPreparedListener{
            binding?.ibPlay?.isEnabled = true
            playerState = State.PREPARED
        }
        mediaPlayer?.setOnCompletionListener {
            binding?.ibPlay?.setImageResource(R.drawable.button_play)
            playerState = State.PREPARED
        }
        DataSource.setValueForMediaActivity(binding!!, this, item)
    }

    private fun exitForTrack(){
        mediaPlayer?.release()
        mediaPlayer = null
    }


    companion object {
        private const val START_TRACK_VALUE = "00:00"
        private const val TIME_VALUE_STEP = 300L
        private const val PATTERN = "mm:ss"
    }
}


