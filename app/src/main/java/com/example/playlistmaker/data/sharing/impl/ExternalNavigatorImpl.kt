package com.example.playlistmaker.data.sharing.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.playlistmaker.R
import com.example.playlistmaker.data.sharing.ExternalNavigator

class ExternalNavigatorImpl(private val context: Context) : ExternalNavigator {

    override fun shareLink(url: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = context.getString(R.string.type_share_text)
        }
        val shareIntent = Intent
            .createChooser(sendIntent, null)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(shareIntent)
    }

    override fun openUserAgreement(url: String) {
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }

    override fun openEmail(email: String) {
        val message = context.getString(R.string.email_message)
        val theme = context.getString(R.string.email_theme)
        Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(context.getString(R.string.url_mail))
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, theme)
            putExtra(Intent.EXTRA_TEXT, message)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }
}