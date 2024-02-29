package ru.practicum.android.diploma.vacancy.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.practicum.android.diploma.vacancy.domain.api.ExternalNavigator

class ExternalNavigatorImpl(private val context: Context) : ExternalNavigator {
    override fun makeCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startImplicitIntent(intent)
    }

    override fun sendEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, "")
            putExtra(Intent.EXTRA_TEXT, "")
        }
        startImplicitIntent(intent)
    }

    override fun shareVacancy(url: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
        }
        startImplicitIntent(intent)
    }

    private fun startImplicitIntent(intent: Intent) {
        if (isIntentSafe(intent)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    private fun isIntentSafe(intent: Intent): Boolean {
        val activities = context.packageManager.queryIntentActivities(intent, 0)
        return activities.size > 0
    }
}
