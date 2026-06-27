package ru.practicum.android.diploma.util

import android.text.Spanned
import androidx.core.text.HtmlCompat

/**
 * Утилита для преобразования HTML-строк в форматированный текст (Spanned).
 */
object HtmlUtils {

    /**
     * Преобразует HTML-строку в Spanned.
     * Если строка пустая или null, возвращает пустой Spanned.
     */
    fun parseHtml(html: String?): Spanned {
        if (html.isNullOrEmpty()) {
            return HtmlCompat.fromHtml("", HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
        return HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
}
