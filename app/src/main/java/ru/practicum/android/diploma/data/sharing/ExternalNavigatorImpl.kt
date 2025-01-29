package ru.practicum.android.diploma.data.sharing

import android.content.Context
import android.content.Intent
import ru.practicum.android.diploma.domain.sharing.api.ExternalNavigator

class ExternalNavigatorImpl(private val context: Context) : ExternalNavigator {

    companion object {
        private const val SHARE_INTENT_TYPE = "text/plain"
    }

    override fun shareVacancy(vacancyLink: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, vacancyLink)
            type = SHARE_INTENT_TYPE
        }

        val shareIntent = Intent.createChooser(intent, null)
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(shareIntent)
    }
}
