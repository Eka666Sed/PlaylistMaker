package com.example.playlistmaker.ui.media

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityMediaBinding
import com.google.android.material.tabs.TabLayoutMediator

class MediaActivity : AppCompatActivity() {

    private var binding: ActivityMediaBinding? = null

    private lateinit var tabMediator: TabLayoutMediator
    private val tabsTitleResIds = arrayOf(
        R.string.favorite_tracks,
        R.string.playlists
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.toolbar?.setNavigationOnClickListener { onBackPressed() }
        initTabLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }

    private fun initTabLayout() = binding?.run {
        viewPager.adapter = MediaViewPagerAdapter(supportFragmentManager, lifecycle)
        tabMediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(tabsTitleResIds[position])
        }
        tabMediator.attach()
    }
}


