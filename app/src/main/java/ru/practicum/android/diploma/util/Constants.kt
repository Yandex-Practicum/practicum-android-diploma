package ru.practicum.android.diploma.util

import android.net.Uri
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R

object Constants {
    val PLACEHOLDER_URI: Uri = Uri.parse(
        "android.resource://${BuildConfig.APPLICATION_ID}${R.drawable.ic_placeholder}"
    )
    val DEFAULT_PLAYLIST_IMAGE_URI: Uri = Uri.parse(
        "android.resource://${BuildConfig.APPLICATION_ID}${R.drawable.ic_employer_default_logo_for_db}"
    )
}
