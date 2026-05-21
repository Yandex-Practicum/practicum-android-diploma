package ru.practicum.android.diploma.presentation.ui.theme

// Использование: salary.currency.toCurrencySymbol() → "₽", "$" и т.д.
fun String?.toCurrencySymbol(): String = when (this) {
    "RUR", "RUB" -> "₽"
    "BYR"        -> "Br"
    "USD"        -> "$"
    "EUR"        -> "€"
    "KZT"        -> "₸"
    "UAH"        -> "₴"
    "AZN"        -> "₼"
    "UZS"        -> "сум"
    "GEL"        -> "₾"
    "KGT"        -> "сом"
    else         -> this.orEmpty()
}
