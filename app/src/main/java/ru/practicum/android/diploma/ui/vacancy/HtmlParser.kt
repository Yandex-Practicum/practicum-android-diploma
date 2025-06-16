package ru.practicum.android.diploma.ui.vacancy

import androidx.core.text.HtmlCompat

class HtmlParser {
    fun fromHtml(source: String): String {
        return HtmlCompat.fromHtml(source, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
    }
}
