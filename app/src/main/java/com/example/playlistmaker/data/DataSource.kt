package com.example.playlistmaker.data

import android.content.Context
import androidx.core.view.isVisible
import com.example.playlistmaker.data.entities.Track
import com.example.playlistmaker.databinding.ActivityMediaBinding
import com.example.playlistmaker.databinding.ItemTrackListBinding
import com.example.playlistmaker.util.ObjectCollection.formatedData
import com.example.playlistmaker.util.ObjectCollection.image

object DataSource {

    fun setValueForMediaActivity(binding: ActivityMediaBinding, context: Context, item: Track) {
        binding.tvArtistName.text = item.artistName
        binding.tvTrackName.text = item.trackName
        if (item.collectionName.isNotEmpty()) binding.tvAlbumValue.text = item.collectionName
        else {
            binding.tvAlbumValue.isVisible = false
            binding.tvAlbum.isVisible = false
        }
        binding.tvCountryValue.text = item.country
        binding.tvYearValue.text = formatedData.getYearFormReleaseDate(item)
        binding.tvDurationValue.text = formatedData.getFormattedTrackTime(item).replaceFirst("0", "")
        binding.tvGenreValue.text = item.primaryGenreName
        image.getRecomendationImage(context, true, binding.ivMain, item)
    }

    fun setValueForViewHolder(binding:ItemTrackListBinding,track:Track,context: Context){
        binding.tvArtistName.text = track.artistName
        binding.tvTrackName.text = track.trackName
        binding.tvTrackTime.text = formatedData.getFormattedTrackTime(track)
        image.getRecomendationImage(context, false, binding.artworkUrl100, track)
    }
}