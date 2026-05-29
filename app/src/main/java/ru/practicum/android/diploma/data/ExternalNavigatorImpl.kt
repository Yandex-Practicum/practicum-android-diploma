package ru.practicum.android.diploma.data

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import ru.practicum.android.diploma.domain.api.ExternalNavigator

class ExternalNavigatorImpl(
    private val context: Context
) : ExternalNavigator {
    override fun openEmail(email: String, title: String) {
        val emailIntent = Intent().apply {
            action = Intent.ACTION_SENDTO
            data = "mailto:".toUri()
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }

        val chooserIntent = Intent.createChooser(emailIntent, title).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(chooserIntent)
    }

    override fun makePhoneCall(phoneNumber: String, title: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_DIAL
            data = "tel:$phoneNumber".toUri()
        }

        val chooserIntent = Intent.createChooser(intent, title).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(chooserIntent)
    }

    override fun shareLink(vacancyLink: String, title: String) {
        val intent = Intent().apply {
            type = TEXT_PLAIN
            putExtra(Intent.EXTRA_TEXT, vacancyLink)
            action = Intent.ACTION_SEND
        }

        val chooserIntent = Intent.createChooser(intent, title).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(chooserIntent)
    }

    companion object {
        const val TEXT_PLAIN = "text/plain"
    }
}
