package ru.practicum.android.diploma.presentation.ui.components.vacancy

import ru.practicum.android.diploma.domain.models.VacancySalary
import ru.practicum.android.diploma.presentation.ui.theme.toCurrencySymbol
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun VacancySalary?.formatSalary(isDetails: Boolean = false): String {
    val fallback = if (isDetails) "Уровень зарплаты не указан" else SALARY_NOT_SPECIFIED
    if (this == null) {
        return fallback
    }

    val fromText = from?.formatAmountWithCurrency(currency)
    val toText = to?.formatAmountWithCurrency(currency)

    return when {
        fromText != null && toText != null -> "от $fromText до $toText"
        fromText != null -> "от $fromText"
        toText != null -> "до $toText"
        else -> fallback
    }
}

private fun Int.formatAmountWithCurrency(currency: String?): String {
    return "${amountFormat.format(this)} ${currency.toCurrencySymbol()}"
}

private val amountFormat = DecimalFormat(
    "#,###",
    DecimalFormatSymbols(Locale.US).apply {
        groupingSeparator = ' '
    },
)

private const val SALARY_NOT_SPECIFIED = "Зарплата не указана"
