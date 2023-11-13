package ru.practicum.android.diploma.util

object CurrencyLogoCreator {
    fun getSymbol(currency: String?): String {
        return when (currency) {
            Currency.MANAT.currency -> "\u20bc"
            Currency.BELORUS_RUBLE.currency -> "\u0072"
            Currency.EURO.currency -> "\u20ac"
            Currency.LARI.currency -> "\u20be"
            Currency.SOM.currency -> "\u043b"
            Currency.TENGE.currency -> "\u043b"
            Currency.RUSSIAN_RUBLE.currency -> "\u20bd"
            Currency.DOLLAR.currency -> "\u0024"
            Currency.SUM.currency -> "\u043b"
            else -> ""
        }
    }
}