package ru.practicum.android.diploma.data.sharing

import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.practicum.android.diploma.domain.api.sharing.ExternalNavigator

class ExternalNavigatorImpl(private val context: Context) : ExternalNavigator {

    override fun shareApp(url: String) {
        Intent().apply {
            action = Intent.ACTION_SEND
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
            context.startActivity(this)
        }
    }

    override fun makeCall(phone: String) {
        Intent().apply {
            action = Intent.ACTION_DIAL
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.parse("tel:$phone")
            context.startActivity(this)
        }
    }

    override fun openEmail(email: String) {
        Intent().apply {
            action = Intent.ACTION_SENDTO
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            context.startActivity(this)
        }
    }
}
