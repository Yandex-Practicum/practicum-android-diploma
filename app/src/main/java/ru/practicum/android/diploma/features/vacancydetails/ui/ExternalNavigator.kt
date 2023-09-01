package ru.practicum.android.diploma.features.vacancydetails.ui

import android.content.Intent
import android.net.Uri
import ru.practicum.android.diploma.features.vacancydetails.domain.models.Email

class ExternalNavigator {

    fun getShareIntent(message: String, chooserMessage: String): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
        return Intent.createChooser(shareIntent, chooserMessage)
    }

    fun getEmailIntent(email: Email): Intent {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email.address))
        email.subject?.let { emailIntent.putExtra(Intent.EXTRA_SUBJECT, email.subject) }
        return Intent.createChooser(emailIntent, null)
    }

    fun getDialIntent(phoneNumber: String): Intent {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$phoneNumber")
        return Intent.createChooser(dialIntent, null)
    }
}