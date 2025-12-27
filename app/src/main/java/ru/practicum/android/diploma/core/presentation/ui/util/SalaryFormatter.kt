package ru.practicum.android.diploma.core.presentation.ui.util

import ru.practicum.android.diploma.search.domain.model.Salary
import java.text.NumberFormat
import java.util.Locale

fun formatSalary(
    from: Int?,
    to: Int?,
    currency: String?,
): String? {
    if (from == null && to == null) return null

    val currencySymbol = currency.toCurrencySymbol()
    val numberFormat = NumberFormat.getIntegerInstance(Locale("ru", "RU"))

    return when {
        from != null && to != null -> {
            "От ${numberFormat.format(from)} до ${numberFormat.format(to)} $currencySymbol"
        }

        from != null -> {
            "От ${numberFormat.format(from)} $currencySymbol"
        }

        to != null -> {
            "До ${numberFormat.format(to)} $currencySymbol"
        }

        else -> null
    }
}

fun Salary?.format(): String? =
    this?.let {
        formatSalary(
            from = it.from,
            to = it.to,
            currency = it.currency,
        )
    }

private fun String?.toCurrencySymbol(): String =
    when (this) {
        "RUR", "RUB" -> "₽"
        "USD" -> "$"
        "EUR" -> "€"
        "GBP" -> "£"
        "HKD" -> "HK$"
        "AUD" -> "A$"
        "SGD" -> "S$"
        "NZD" -> "NZ$"
        "SEK" -> "SEK"
        else -> this.orEmpty()
    }
