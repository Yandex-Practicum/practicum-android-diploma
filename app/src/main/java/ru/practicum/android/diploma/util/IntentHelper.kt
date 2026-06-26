package ru.practicum.android.diploma.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import ru.practicum.android.diploma.R

/**
 * Помощник для работы с внешними Интентами (звонки, почта, браузер).
 */
object IntentHelper {

    /**
     * Открывает диалер с предзаполненным номером телефона.
     */
    fun callPhone(context: Context, phoneNumber: String?) {
        if (phoneNumber.isNullOrBlank()) return
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startIntentSafely(context, intent)
    }

    /**
     * Открывает почтовый клиент для отправки письма.
     */
    fun sendEmail(context: Context, email: String?, subject: String? = null, body: String? = null) {
        if (email.isNullOrBlank()) return
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            subject?.let { putExtra(Intent.EXTRA_SUBJECT, it) }
            body?.let { putExtra(Intent.EXTRA_TEXT, it) }
        }
        startIntentSafely(context, intent)
    }

    /**
     * Открывает внешнюю ссылку в браузере.
     */
    fun openBrowser(context: Context, url: String?) {
        if (url.isNullOrBlank()) return
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startIntentSafely(context, intent)
    }

    /**
     * Поделиться текстовой ссылкой (например, вакансией).
     */
    fun shareText(context: Context, text: String?, title: String? = null) {
        if (text.isNullOrBlank()) return
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        val chooser = Intent.createChooser(intent, title)
        startIntentSafely(context, chooser)
    }

    private fun startIntentSafely(context: Context, intent: Intent) {
        try {
            context.startActivity(intent)
        } catch (_: android.content.ActivityNotFoundException) {
            Toast.makeText(context, context.getString(R.string.no_suitable_app), Toast.LENGTH_SHORT).show()
        }
    }
}
