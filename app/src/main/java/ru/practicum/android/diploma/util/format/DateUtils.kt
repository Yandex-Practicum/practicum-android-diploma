/**Метод для форматирования даты из формата API в формат для отображения пользователю
 */
package ru.practicum.android.diploma.util.format

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {
    @SuppressLint("ConstantLocale")
    private val apiDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())

    @SuppressLint("ConstantLocale")
    private val displayDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    fun formatDateToDisplay(dateString: String?): String? {
        return try {
            val date = apiDateFormat.parse(dateString ?: return null)
            displayDateFormat.format(date)
        } catch (e: ParseException) {
            null
        }
    }
}
