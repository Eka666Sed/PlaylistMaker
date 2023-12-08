package com.example.playlistmaker.domain.sharing.model

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.playlistmaker.R
import com.example.playlistmaker.data.setting.Constant
import com.example.playlistmaker.creator.ConverterCreator
import com.example.playlistmaker.data.player.Track
import com.example.playlistmaker.ui.media.activity.MediaActivity

object IntentWorkPlace {
    fun shareText(context: Context, text: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = context.getString(R.string.type_share_text)
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }


    fun openEmailApp(context: Context,) {
        val message = context.getString(R.string.email_message)
        val theme = context.getString(R.string.email_theme)
        Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(context.getString(R.string.url_mail))
            putExtra(Intent.EXTRA_EMAIL, arrayOf(context.getString(R.string.my_email)))
            putExtra(Intent.EXTRA_SUBJECT, theme)
            putExtra(Intent.EXTRA_TEXT, message)
            context.startActivity(this)
        }
    }

    fun parseWeb(context: Context) {
        val url = context.getString(R.string.web_url)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    fun sendData(context: Context, item: Track){
        val itemJson = ConverterCreator.sharedPreferenceConverter().createJsonFromTrack(item)
        val intent = Intent(context, MediaActivity::class.java)
        intent.putExtra(Constant.KEY, itemJson)
        context.startActivity(intent)
    }
}