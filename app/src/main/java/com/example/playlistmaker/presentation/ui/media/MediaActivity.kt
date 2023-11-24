package com.example.playlistmaker.presentation.ui.media

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.playlistmaker.R
import com.example.playlistmaker.app.utils.Constant
import com.example.playlistmaker.databinding.ActivityMediaBinding
import com.example.playlistmaker.domain.ConverterCreator
import com.example.playlistmaker.domain.MediaCreator
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.utils.DataFormatter
import com.example.playlistmaker.domain.utils.State
import com.example.playlistmaker.presentation.utils.ImageWorkPlace
import java.text.SimpleDateFormat
import java.util.Locale

class MediaActivity : AppCompatActivity() {
    private var binding: ActivityMediaBinding? = null
    private var mediaPlayer: MediaPlayer? = null
    private var mediaUri: Uri? = null
    private val handler = Handler()
    private var progressRunnable: Runnable? = null
    private val mediaCreator = MediaCreator.providePlayerInteractor()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.tvTimeTrack?.text = START_TRACK_VALUE
        binding?.toolbarMedia?.setNavigationOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
                mediaCreator.playerState = State.PAUSED
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
                else {
                    mediaCreator.pausePlayer(mediaPlayer!!,binding!!)
                }
            }
        }
        mediaPlayer?.setOnCompletionListener {
            binding?.tvTimeTrack?.text = START_TRACK_VALUE
            mediaCreator.pausePlayer(mediaPlayer!!,binding!!)
        }

    }

    override fun onResume() {
        super.onResume()
        binding?.ibPlay?.setOnClickListener {
            mediaCreator.playbackControl(mediaPlayer!!,binding!!)
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
            binding?.let { bind -> mediaCreator.pausePlayer(player,bind) }
        }
    }

    override fun onStop() {
        super.onStop()
        if ( mediaCreator.playerState != State.PAUSED) {
            handler.removeCallbacks(progressRunnable!!)
            exitForTrack()
        }
    }

    private fun setDataForView(data: String) {
        val item = ConverterCreator.sharedPreferenceConverter().createTrackFromJson(data)
        mediaUri = Uri.parse(item.previewUrl)
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource(applicationContext, mediaUri!!)
        mediaPlayer?.prepareAsync()
        mediaPlayer?.setOnPreparedListener{
            binding?.ibPlay?.isEnabled = true
            mediaCreator.playerState = State.PREPARED
        }
        mediaPlayer?.setOnCompletionListener {
            binding?.ibPlay?.setImageResource(R.drawable.button_play)
            mediaCreator.playerState = State.PREPARED
        }
        setValueForMediaActivity(this,item)
    }

    private fun exitForTrack(){
        mediaPlayer?.release()
        mediaPlayer = null
    }
    private fun setValueForMediaActivity(context: Context, item: Track) {
        binding?.tvArtistName?.text = item.artistName
        binding?.tvTrackName?.text = item.trackName
        if (item.collectionName.isNotEmpty()) binding?.tvAlbumValue?.text = item.collectionName
        else {
            binding?.tvAlbumValue?.isVisible = false
            binding?.tvAlbum?.isVisible = false
        }
        binding?.tvCountryValue?.text = item.country
        binding?.tvYearValue?.text = DataFormatter.getYearFromReleaseDate(item)
        binding?.tvDurationValue?.text = DataFormatter.getFormattedTrackTime(item).replaceFirst("0", "")
        binding?.tvGenreValue?.text = item.primaryGenreName
        ImageWorkPlace.getRecomendationImage(context, true, binding!!.ivMain, item)
    }

    companion object {
        private const val START_TRACK_VALUE = "00:00"
        private const val TIME_VALUE_STEP = 300L
        private const val PATTERN = "mm:ss"
    }
}