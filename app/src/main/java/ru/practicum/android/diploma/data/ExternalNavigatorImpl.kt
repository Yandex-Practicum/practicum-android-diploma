package ru.practicum.android.diploma.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.ExternalNavigator
import ru.practicum.android.diploma.domain.models.Email

class ExternalNavigatorImpl(
    private val appContext: Context
) : ExternalNavigator {
    override fun openUrlLink(link: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(link)
        ).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
        ContextCompat.startActivity(appContext, intent, null)
    }

    override fun sendEmail(emailData: Email) {
        val sendEmailIntent = Intent().apply {
            action = Intent.ACTION_SENDTO
            data = Uri.parse(appContext.getString(R.string.uri_mailto))
            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailData.emailAddress))
            putExtra(Intent.EXTRA_SUBJECT, emailData.subject)
            putExtra(Intent.EXTRA_TEXT, emailData.text)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            Intent.createChooser(this, null)
        }
        ContextCompat.startActivity(appContext, sendEmailIntent, null)
    }

    override fun share(link: String) {
        val shareLinkIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = appContext.getString(R.string.text_plain)
            putExtra(Intent.EXTRA_TEXT, link)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            Intent.createChooser(this, null)
        }
        ContextCompat.startActivity(appContext, shareLinkIntent, null)
    }

    override fun makePhoneCall(number: String) {
        val phoneCallIntent = Intent().apply {
            action = Intent.ACTION_DIAL
            data = Uri.parse(appContext.getString(R.string.tel, number))
        }
        val chooser = Intent.createChooser(phoneCallIntent, null).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        ContextCompat.startActivity(appContext, chooser, null)
    }
}
