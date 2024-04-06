package ru.practicum.android.diploma.data.sharing

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import ru.practicum.android.diploma.domain.sharing.ExternalNavigator

class ExternalNavigatorImpl(
    private val context: Context
) : ExternalNavigator {
    override fun writeEmail(address: String) {
        val shareIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, address)
        }
        val chooserIntent = Intent.createChooser(shareIntent, null).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(chooserIntent)
    }

    override fun call(phone: String) {
        val callIntent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phone")
        }
        val chooserIntent = Intent.createChooser(callIntent, null).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(chooserIntent)
    }

    override fun openApplicationSettings() {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }
}
