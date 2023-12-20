package com.example.playlistmaker.ui.player

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.playlistmaker.R
import com.example.playlistmaker.app.utils.ImageWorkPlace
import com.example.playlistmaker.databinding.ActivityPlayerBinding
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.player.model.PlayerState
import com.example.playlistmaker.domain.utils.DateFormatter
import com.example.playlistmaker.ui.player.view_model.PlayerViewModel

class PlayerActivity : AppCompatActivity() {
    private var binding: ActivityPlayerBinding? = null
    private lateinit var viewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        viewModel = ViewModelProvider(this).get<PlayerViewModel>()
        initViews()
        initObservers()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    private fun initViews(){
        binding?.apply {
            toolbarMedia.setNavigationOnClickListener { onBackPressed() }
            ibLike.setOnClickListener { viewModel.onLikeButtonClicked() }
            ibPlay.setOnClickListener { viewModel.onPlayButtonClicked() }
        }
    }

    private fun initObservers() {
        viewModel.state.observe(this) {
            it.track?.let { track -> setTrackData(track) }
            binding?.tvTimeTrack?.text = it.trackTime.ifEmpty {
                getString(R.string.start_track_time)
            }
            it?.playerState?.let { state ->
                when (state) {
                    PlayerState.PLAYING -> binding?.ibPlay?.setImageResource(R.drawable.button_pause)
                    PlayerState.PREPARED,
                    PlayerState.PAUSED -> binding?.ibPlay?.setImageResource(R.drawable.button_play)
                }
            }
        }

        viewModel.event.observe(this) {
            Toast.makeText(this, getString(R.string.playlist_create), Toast.LENGTH_LONG).show()
        }
    }

    private fun setTrackData(track: Track) {
        binding?.apply {
            tvArtistName.text = track.artistName
            tvTrackName.text = track.trackName
            if (track.collectionName.isNotEmpty())
                tvAlbumValue.text = track.collectionName
            else {
                tvAlbumValue.isVisible = false
                tvAlbum.isVisible = false
            }
            tvCountryValue.text = track.country
            tvYearValue.text = DateFormatter.getYearFromReleaseDate(track.releaseDate)
            tvDurationValue.text = DateFormatter.formatMillisToString(track.trackTimeMillis).replaceFirst("0", "")
            tvGenreValue.text = track.primaryGenreName
            ImageWorkPlace.getRecommendationImage(this@PlayerActivity, ivMain, track.artworkUrl100)
        }
    }
}