package ru.practicum.android.diploma.util.extensions

import android.content.Context
import android.icu.text.NumberFormat
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.main.Salary
import ru.practicum.android.diploma.util.Currency
import java.util.Locale

fun Salary?.toFormattedString(context: Context): String {
    if (this == null || from == null && to == null) {
        return context.getString(R.string.salary_not_specified)
    }

    val currencySymbol = Currency.getCurrencySymbol(currency.orEmpty())
    val grossLabel = gross.toGrossLabel(context)
    val salaryPart = formatSalaryRange(context, from, to, currencySymbol)

    return salaryPart + grossLabel
}

private fun formatSalaryRange(context: Context, from: Int?, to: Int?, symbol: String): String {
    val formattedFrom = from?.let { formatNumber(it) }
    val formattedTo = to?.let { formatNumber(it) }

    return when {
        from != null && to == null -> context.getString(R.string.salary_from, formattedFrom, symbol)
        from == null && to != null -> context.getString(R.string.salary_to, formattedTo, symbol)
        from != null && to != null -> context.getString(R.string.salary_from_to, formattedFrom, formattedTo, symbol)
        else -> context.getString(R.string.salary_not_specified)
    }
}

private fun Boolean?.toGrossLabel(context: Context): String = when (this) {
    true -> " ${context.getString(R.string.salary_gross)}"
    false -> " ${context.getString(R.string.salary_net)}"
    null -> ""
}

private fun formatNumber(value: Int): String {
    return NumberFormat.getNumberInstance(Locale.getDefault()).format(value)
}
