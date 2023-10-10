package com.example.playlistmaker

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.databinding.ActivityMediaBinding

class MediaActivity : AppCompatActivity() {
    private var binding: ActivityMediaBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.toolbarMedia?.setNavigationOnClickListener { onBackPressed() }
        val itemJson = intent.getStringExtra(Constant.KEY)
        itemJson?.let { setDataForView(it) }
        binding?.ibLike?.setOnClickListener {
            binding?.ibLike?.setImageResource(R.drawable.like)
            Toast.makeText(this,  getString(R.string.playlist_create), Toast.LENGTH_LONG).show()
        }
    }

    private fun setDataForView(data: String) {
        val item = SharedPreferenceConverter.createTrackFromJson(data)
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
        binding?.tvTimeTrack?.text = item.getFormattedTrackTime().replaceFirst("0", "")
    }

}

