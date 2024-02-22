package ru.practicum.android.diploma.vacancy.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.practicum.android.diploma.vacancy.domain.api.ExternalNavigator

class ExternalNavigatorImpl(private val context: Context) : ExternalNavigator {
    override fun makeCall(phoneNumber: String) {
        Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }

    override fun sendEmail(email: String) {
        Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }

    override fun shareVacancy() {
        TODO("Not yet implemented")
    }

}
