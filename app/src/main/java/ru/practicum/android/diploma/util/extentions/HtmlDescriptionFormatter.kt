package ru.practicum.android.diploma.util.extentions

import androidx.core.text.HtmlCompat

fun String?.formatHtmlDescription(): String {
    if (isNullOrBlank()) {
        return ""
    }
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim()
}
