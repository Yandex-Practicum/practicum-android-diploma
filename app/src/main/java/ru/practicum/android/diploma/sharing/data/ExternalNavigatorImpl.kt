package ru.practicum.android.diploma.sharing.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.practicum.android.diploma.sharing.domain.ExternalNavigator

class ExternalNavigatorImpl(private val context: Context) : ExternalNavigator {

    override fun shareLink(link: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, link)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun openEmail(email: String) {
        val intentEmail = Intent(Intent.ACTION_SENDTO)
        intentEmail.data = Uri.parse("mailto:")
        intentEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf(java.lang.String(email)))
        intentEmail.putExtra(Intent.EXTRA_SUBJECT, "subject")
        intentEmail.putExtra(Intent.EXTRA_TEXT, "message")
        intentEmail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intentEmail)
    }

    override fun openAppForCalls(number: String) {
        val call = Uri.parse("tel:$number")
        val intentCall = Intent(Intent.ACTION_DIAL, call)
        intentCall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intentCall)
    }
}
