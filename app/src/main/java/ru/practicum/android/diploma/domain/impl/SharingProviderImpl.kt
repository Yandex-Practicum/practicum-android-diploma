package ru.practicum.android.diploma.domain.impl

import android.content.Intent
import ru.practicum.android.diploma.domain.api.ISharingProvider

class SharingProviderImpl(private val message: String) : ISharingProvider {
    override fun shareVacancy() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.type = "text/plain"
    }
}
