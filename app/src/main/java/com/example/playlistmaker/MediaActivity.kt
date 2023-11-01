package com.example.playlistmaker

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.playlistmaker.databinding.ActivityMediaBinding
import java.text.SimpleDateFormat
import java.util.Locale

class MediaActivity : AppCompatActivity() {
    private var binding: ActivityMediaBinding? = null
    private var mediaPlayer: MediaPlayer? = null
    private var mediaUri: Uri? = null
    private var playerState:State? = null
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
                playerState = State.PAUSED
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
                else { pausePlayer() }
            }
        }
        mediaPlayer?.setOnCompletionListener {
            binding?.tvTimeTrack?.text = START_TRACK_VALUE
            pausePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.ibPlay?.setOnClickListener {
            playbackControl()
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
        pausePlayer()
    }

    override fun onStop() {
        super.onStop()
        if (playerState != State.PAUSED) {
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
        binding?.tvArtistName?.text = item.artistName
        binding?.tvTrackName?.text = item.trackName
        if (item.collectionName.isNotEmpty()) binding?.tvAlbumValue?.text = item.collectionName
        else {
            binding?.tvAlbumValue?.isVisible = false
            binding?.tvAlbum?.isVisible = false
        }
        binding?.tvCountryValue?.text = item.country
        binding?.tvYearValue?.text = item.getYearFormReleaseDate()
        binding?.tvDurationValue?.text = item.getFormattedTrackTime().replaceFirst("0", "")
        binding?.tvGenreValue?.text = item.primaryGenreName
        ImageWorkPlace.getRecomendationImage(this, true, binding?.ivMain!!, item)
    }

    private fun exitForTrack(){
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun pausePlayer() {
        mediaPlayer?.pause()
        binding?.ibPlay?.setImageResource(R.drawable.button_play)
        playerState = State.PAUSED
    }

    private fun startPlayer(){
        if (!mediaPlayer?.isPlaying!!) {
            mediaPlayer?.start()
            binding?.ibPlay?.setImageResource(R.drawable.button_pause)
            playerState = State.PLAYING
        }
    }

    private fun playbackControl() {
        when(playerState) {
            State.PLAYING -> pausePlayer()
            State.PREPARED, State.PAUSED -> startPlayer()
            State.DEFAULT -> {}
            else -> {}
        }
    }

    companion object {
        private const val START_TRACK_VALUE = "00:00"
        private const val TIME_VALUE_STEP = 300L
        private const val PATTERN = "mm:ss"
    }
}


