package ru.practicum.android.diploma.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.practicum.android.diploma.domain.ExternalNavigator

class ExternalNavigatorImpl(private val context: Context) : ExternalNavigator {

    override fun sharePhone(phone: String) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$phone")
        context.startActivity(callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    override fun shareEmail(email: String) {
        val writeSupport = Intent(Intent.ACTION_SENDTO)
        writeSupport.data = Uri.parse("mailto:")
        writeSupport.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        context.startActivity(writeSupport.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    override fun shareVacancyUrl(vacancyUrl: String) {
        val sendIntent : Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, vacancyUrl)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}