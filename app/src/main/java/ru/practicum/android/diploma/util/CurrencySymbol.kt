package ru.practicum.android.diploma.util

object CurrencySymbol {
    fun getCurrencySymbol(currency: String): String {
        return when (currency) {
            "AZN" -> "\u20bc"
            "BYR" -> "Br"
            "EUR" -> "\u20ac"
            "GEL" -> "\u20be"
            "KGS" -> "Som"
            "KZT" -> "\u20b8"
            "RUR" -> "\u20bd"
            "UAH" -> "\u20b4"
            "USD" -> "\u0024"
            "UZS" -> "сўм"
            else -> {
                ""
            }
        }
    }
}
