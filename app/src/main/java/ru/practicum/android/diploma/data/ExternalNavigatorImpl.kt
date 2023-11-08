package ru.practicum.android.diploma.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.ExternalNavigator

class ExternalNavigatorImpl(private val context: Context): ExternalNavigator {

    override fun sharePhone(phone: RecyclerView) {
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
}