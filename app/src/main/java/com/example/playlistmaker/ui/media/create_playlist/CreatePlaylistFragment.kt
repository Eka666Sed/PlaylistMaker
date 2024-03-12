package com.example.playlistmaker.ui.media.create_playlist

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentCreatePlaylistBinding
import com.example.playlistmaker.ui.media.create_playlist.view_model.CreatePlaylistViewModel
import com.example.playlistmaker.ui.media.create_playlist.view_model.CreatePlaylistViewModel.Companion.KEY_PLAYLIST_COVER_URI
import com.example.playlistmaker.ui.util.ResultKeyHolder
import com.example.playlistmaker.ui.util.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

open class CreatePlaylistFragment : Fragment() {

    protected var binding: FragmentCreatePlaylistBinding? = null
    protected open val viewModel: CreatePlaylistViewModel by viewModel()
    private val pickMedia = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) {
        it?.let { uri -> viewModel.onPlaylistCoverSelected(uri) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatePlaylistBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val playlistCoverUri = savedInstanceState?.getParcelable<Uri>(KEY_PLAYLIST_COVER_URI)
        playlistCoverUri?.let { viewModel.onPlaylistCoverSelected(it) }
        initToolbar()
        setupTextListeners()
        initCompleteButton()
        initCoverButton()
        setupBackPressHandling()
        initObservers()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_PLAYLIST_COVER_URI, viewModel.playlistCoverUri.value)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    protected open fun initToolbar() {
        binding?.toolbar?.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    protected open fun initCompleteButton() {
        binding?.apply {
            btnComplete.setOnClickListener { viewModel.onCompleteButtonClicked() }
        }
    }

    private fun setupTextListeners() {
        binding?.apply {
            editTextName.doAfterTextChanged { viewModel.onPlaylistNameChanged(it.toString()) }
            editTextDescription.doAfterTextChanged {
                viewModel.onPlaylistDescriptionChanged(it.toString())
            }
        }
    }

    private fun initCoverButton() {
        binding?.apply {
            ibCover.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
    }

    private fun setupBackPressHandling() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isEnabled) {
                    viewModel.onBackPressed()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun initObservers() {
        viewModel.isButtonCreateEnabled.observe(viewLifecycleOwner) {
            binding?.btnComplete?.isEnabled = it
        }

        viewModel.playlistCoverUri.observe(viewLifecycleOwner) {
            it?.let { setSelectedCover(it) }
        }

        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                is CreatePlaylistEvent.NavigateBack -> findNavController().popBackStack()
                is CreatePlaylistEvent.ShowBackConfirmationDialog -> showBackConfirmationDialog()
                is CreatePlaylistEvent.SetPlaylistCreatedResult -> {
                    requireActivity().supportFragmentManager.setFragmentResult(
                        ResultKeyHolder.KEY_PLAYLIST_CREATED,
                        bundleOf(ResultKeyHolder.KEY_PLAYLIST_NAME to it.playlistName)
                    )
                }
            }
        }
    }

    private fun showBackConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.confirmation_dialog_title))
            .setMessage(getString(R.string.confirmation_dialog_message))
            .setNegativeButton(getString(R.string.confirmation_dialog_negative)) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(getString(R.string.confirmation_dialog_positive)) { _, _ -> viewModel.onBackPressedConfirmed() }
            .show()
    }

    private fun setSelectedCover(uri: Uri) {
        binding?.ibCover?.load(uri.toString())
    }
}