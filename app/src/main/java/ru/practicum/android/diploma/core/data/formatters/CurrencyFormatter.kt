package ru.practicum.android.diploma.core.data.formatters

import android.content.Context
import ru.practicum.android.diploma.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class CurrencyFormatter(val context: Context) {
    fun format(
        from: Int?,
        to: Int?,
        currency: String?
    ): String {
        var currencySymbol = when (currency?.uppercase()) {
            "RUR", "RUB" -> "₽"
            "BYR" -> "Br"
            "USD" -> "$"
            "EUR" -> "€"
            "KZT" -> "₸"
            "UAH" -> "₴"
            "AZN" -> "₼"
            "UZS" -> "сум"
            "GEL" -> "₾"
            "KGT" -> "сом"
            else -> currency ?: ""
        }

        var result = ""
        from?.let { result += "от ${formatNum(it)}" }
        if (from != null && to != null) {
            result += " "
        }
        to?.let { result += "до ${formatNum(it)}" }
        if (currencySymbol.isNotEmpty()) {
            currencySymbol = " $currencySymbol"
        }

        return if (result.isNotEmpty()) "$result$currencySymbol" else context.getString(R.string.salary_empty)
    }
    fun formatNum(value: Int): String {
        val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
            groupingSeparator = ' '
        }

        val df = DecimalFormat("#,###", symbols)
        return df.format(value)
    }
}
