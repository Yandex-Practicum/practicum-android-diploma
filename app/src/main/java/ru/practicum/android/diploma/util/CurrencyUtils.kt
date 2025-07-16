package ru.practicum.android.diploma.util

object CurrencyUtils {
    fun getCurrencySymbol(currencyCode: String?): String {
        return when (currencyCode?.uppercase()) {
            "RUB", "RUR" -> "₽"
            "USD" -> "$"
            "EUR" -> "€"
            "BYR" -> "Br"
            "KZT" -> "₸"
            "UAH" -> "₴"
            "AZN" -> "₼"
            "UZS" -> "сўм"
            "GEL" -> "₾"
            "KGS" -> "сом"
            else -> currencyCode ?: ""
        }
    }
}
