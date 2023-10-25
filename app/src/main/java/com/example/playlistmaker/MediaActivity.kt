package com.example.playlistmaker

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.databinding.ActivityMediaBinding
import java.text.SimpleDateFormat
import java.util.Locale

class MediaActivity : AppCompatActivity() {
    private var binding: ActivityMediaBinding? = null
    private var mediaPlayer: MediaPlayer? = null
    private var mediaUri: Uri? = null
    private var playerState = STATE_DEFAULT
    private val handler = Handler()
    private lateinit var progressRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.tvTimeTrack?.text = START_TRACK_VALUE
        binding?.toolbarMedia?.setNavigationOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
                playerState = STATE_PAUSED
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
                val currentPosition = SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer?.currentPosition)
                if (mediaPlayer?.isPlaying == true) {
                    binding?.tvTimeTrack?.text = currentPosition
                    handler.postDelayed(this, 300)
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
            handler.post(progressRunnable)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(progressRunnable)
        exitForTrack()
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onStop() {
        super.onStop()
        if (playerState != STATE_PAUSED) {
            handler.removeCallbacks(progressRunnable)
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
            playerState = STATE_PREPARED
        }
        mediaPlayer?.setOnCompletionListener {
            binding?.ibPlay?.setImageResource(R.drawable.button_play)
            playerState = STATE_PREPARED
        }
        binding?.tvArtistName?.text = item.artistName
        binding?.tvTrackName?.text = item.trackName
        if (item.collectionName.isNotEmpty()) binding?.tvAlbumValue?.text = item.collectionName
        else {
            binding?.tvAlbumValue?.visibility = View.GONE
            binding?.tvAlbum?.visibility = View.GONE
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
        playerState = STATE_PAUSED
    }

    private fun startPlayer(){
        if (!mediaPlayer?.isPlaying!!) {
            mediaPlayer?.start()
            binding?.ibPlay?.setImageResource(R.drawable.button_pause)
            playerState = STATE_PLAYING
        }
    }

    private fun playbackControl() {
        when(playerState) {
            STATE_PLAYING -> { pausePlayer() }
            STATE_PREPARED, STATE_PAUSED -> { startPlayer() }
        }
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val START_TRACK_VALUE = "00:00"
    }
}


