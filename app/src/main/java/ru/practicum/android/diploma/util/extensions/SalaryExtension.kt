package ru.practicum.android.diploma.util.extensions

import android.content.Context
import android.icu.text.NumberFormat
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.main.Salary
import ru.practicum.android.diploma.util.Currency
import java.util.Locale

fun Salary?.toFormattedString(context: Context): String {
    if (this == null || (this.from == null && this.to == null)) {
        return context.getString(R.string.salary_not_specified)
    }

    val currencySymbol = Currency.getCurrencySymbol(this.currency ?: "")
    val formattedFrom = this.from?.let { formatNumber(it) }
    val formattedTo = this.to?.let { formatNumber(it) }

    val grossLabel = when (gross) {
        true -> " ${context.getString(R.string.salary_gross)}"
        false -> " ${context.getString(R.string.salary_net)}"
        null -> ""
    }

    return when {
        this.from != null && this.to == null -> context.getString(
            R.string.salary_from,
            formattedFrom,
            currencySymbol
        ) + grossLabel

        this.from == null && this.to != null -> context.getString(
            R.string.salary_to,
            formattedTo,
            currencySymbol
        ) + grossLabel

        this.from != null && this.to != null -> context.getString(
            R.string.salary_from_to,
            formattedFrom,
            formattedTo,
            currencySymbol
        ) + grossLabel

        else -> context.getString(R.string.salary_not_specified)
    }
}

private fun formatNumber(value: Int): String {
    return NumberFormat.getNumberInstance(Locale.getDefault()).format(value)
}
