package ru.practicum.android.diploma.vacancy.data.repositoryimpl

import android.content.Context
import android.content.Intent

internal class ExternalNavigator(private val context: Context) {
    fun shareLink(shareLink: String) {
        Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareLink)
            type = "text/plain"
            Intent.createChooser(this, null)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }
}
