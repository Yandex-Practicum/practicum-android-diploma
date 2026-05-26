package ru.practicum.android.diploma.util.extentions

fun String?.toCurrencySymbol(): String {
    return when (this?.uppercase()) {
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
        else -> ""
    }
}
