package ru.practicum.android.diploma.util

object CurrencySymbol {
    fun get(currency: String?): String =
        when (currency) {
            "AZN" -> "₼"
            "BYR" -> "Br"
            "EUR" -> "€"
            "GEL" -> "₾"
            "KGS" -> "⊆"
            "KZT" -> "₸"
            "RUR" -> "₽"
            "UAH" -> "₴"
            "USD" -> "$"
            "UZS" -> "Soʻm"
            null -> ""
            else -> currency
        }
}
