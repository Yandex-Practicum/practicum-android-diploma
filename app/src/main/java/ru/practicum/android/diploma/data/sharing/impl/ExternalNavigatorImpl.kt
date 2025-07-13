package ru.practicum.android.diploma.data.sharing.impl

import android.content.Context
import android.content.Intent
import ru.practicum.android.diploma.data.sharing.ExternalNavigator

class ExternalNavigatorImpl(private val context: Context) : ExternalNavigator {
    override fun shareVacancy(linkVacancy: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, linkVacancy)
        }

        val chooser = Intent.createChooser(shareIntent, "")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }
}
