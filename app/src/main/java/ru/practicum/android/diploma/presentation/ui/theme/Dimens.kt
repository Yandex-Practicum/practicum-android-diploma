package ru.practicum.android.diploma.presentation.ui.theme

import androidx.compose.ui.unit.dp

object Dimens {

    // Horizontal paddings
    val paddingSmall = 4.dp
    val paddingMedium = 12.dp
    val paddingDefault = 16.dp
    val paddingAlt = 17.dp
    val paddingSystemBar = 8.dp
    val paddingExtraLarge = 46.dp

    // Top / vertical paddings
    val paddingTopTiny = 3.dp
    val paddingTopSmall = 6.dp
    val paddingTopMedium = 9.dp
    val paddingTopLarge = 24.dp

    // Icon sizes
    val iconSizeSmall = 24.dp
    val iconSizeMedium = 48.dp
    val systemBarIconWidth = 46.dp
    val systemBarIconHeight = 10.dp

    // Component heights
    val heightVacancyPanel = 27.dp
    val heightMedium = 64.dp
    val heightLarge = 72.dp
    val heightButton = 59.dp
    val heightMenuItem = 60.dp
    val heightImage = 223.dp

    val cornerRadius = 12.dp
}

// Использование: salary.currency.toCurrencySymbol() → "₽", "$" и т.д.
fun String?.toCurrencySymbol(): String = when (this) {
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
    else -> this.orEmpty()
}
