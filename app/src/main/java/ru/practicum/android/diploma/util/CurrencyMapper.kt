package ru.practicum.android.diploma.util

object CurrencyMapper {
    fun map(code: String?): String = when (code?.uppercase()) {
        "RUB", "RUR" -> "₽"
        "BYN", "BYR" -> "Br"
        "USD" -> "$"
        "EUR" -> "€"
        "KZT" -> "₸"
        "AZN" -> "₼"
        "UZS" -> "сум"
        "KGT", "KGS" -> "сом"
        "GEL" -> "₾"
        "UAH" -> "₴"
        else -> code ?: ""
    }
}
