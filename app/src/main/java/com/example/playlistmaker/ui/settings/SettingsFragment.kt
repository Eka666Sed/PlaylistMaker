package com.example.playlistmaker.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.FragmentSettingsBinding
import com.example.playlistmaker.ui.settings.view_model.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private var binding: FragmentSettingsBinding? = null
    private  val viewModel: SettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpButtons()
        setUpThemeSwitch()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setUpButtons() {
        binding?.apply {
            buttonShareApp.setOnClickListener { viewModel.onShareAppButtonClicked() }
            buttonWriteSupport.setOnClickListener { viewModel.onWriteSupportButtonClicked() }
            buttonUserAgreement.setOnClickListener { viewModel.onUserAgreementsButtonClicked() }
        }
    }

    private fun setUpThemeSwitch() {
        binding?.themeSwither?.apply {
            viewModel.applicationTheme.observe(viewLifecycleOwner) {
                isChecked = it
            }
            setOnClickListener { viewModel.onThemeSwitchClicked(this.isChecked) }
        }
    }
}