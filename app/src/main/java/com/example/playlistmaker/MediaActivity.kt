package com.example.playlistmaker

import android.content.res.Configuration
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
        val itemJson = intent.getStringExtra("key")
        setDataForView(itemJson)
        binding?.ibLike?.setOnClickListener {
            binding?.ibLike?.setImageResource(R.drawable.like)
            Toast.makeText(this,"Плэйлист создан",Toast.LENGTH_LONG).show()
        }
        setImageResourse()
    }
    private fun isNightModeEnabled(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }


    private fun setImageResourse(){
        binding?.ibLike?.setImageResource(
            if (isNightModeEnabled()) R.drawable.like_dark
            else R.drawable.like
        )
        binding?.ibShare?.setImageResource(
            if (isNightModeEnabled()) R.drawable.add_dark
            else R.drawable.add
        )
        binding?.ibPlay?.setImageResource(
            if (isNightModeEnabled()) R.drawable.button_play_dark
            else R.drawable.button_play
        )
    }

    fun setDataForView(data:String?){
        if (data != null) {
            val item = SharedPreferenceConverter.createTrackFromJson(data)
            binding?.tvArtistName?.text = item.artistName
            binding?.tvTrackName?.text = item.trackName
            if(item.collectionName != "" )binding?.tvAlbumValue?.text = item.collectionName
            else{
                binding?.tvAlbumValue?.visibility = View.GONE
                binding?.tvAlbum?.visibility = View.GONE
            }

            binding?.tvCountryValue?.text = item.country
            binding?.tvYearValue?.text = item.getYearFormReleaseDate()
            binding?.tvDurationValue?.text = item.getFormattedTrackTime().replaceFirst("0","")
            binding?.tvGenreValue?.text = item.primaryGenreName
            ImageWorkPlace.getRecomendationImage(this, true, binding?.ivMain!!, item)
            binding?.tvTimeTrack?.text = item.getFormattedTrackTime().replaceFirst("0","")
        }
    }

}

