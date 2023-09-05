package ru.practicum.android.diploma.sharing.data

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import ru.practicum.android.diploma.sharing.domain.api.ExternalNavigator
import javax.inject.Inject

class ExternalNavigatorImpl @Inject constructor(private val context: Context) : ExternalNavigator {
    override fun sendVacancy(link: String?) {
        Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
            Intent.createChooser(this, null)
            addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }

    override fun writeEmail(link: String?) {
        Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$link")
            putExtra(Intent.EXTRA_SUBJECT, "subject")
            putExtra(Intent.EXTRA_TEXT, "body")
            Intent.createChooser(this, null)
            addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }

    }

    override fun makeCall(link: String?) {
        Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$link")
            Intent.createChooser(this, null)
            addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }

    }
}