package com.example.playlistmaker.ui.media.playlist_info.menu

import androidx.annotation.StringRes
import com.example.playlistmaker.R

sealed class PlaylistInfoMenuItem(
    @StringRes
    open val textResId: Int,
    open val onClick: () -> Unit
) {
    data class Share(
        override val textResId: Int = R.string.playlist_menu_share,
        override val onClick: () -> Unit
    ) : PlaylistInfoMenuItem(textResId, onClick)

    data class Edit(
        override val textResId: Int = R.string.playlist_menu_edit,
        override val onClick: () -> Unit
    ) : PlaylistInfoMenuItem(textResId, onClick)

    data class Delete(
        override val textResId: Int = R.string.playlist_menu_delete,
        override val onClick: () -> Unit
    ) : PlaylistInfoMenuItem(textResId, onClick)
}
