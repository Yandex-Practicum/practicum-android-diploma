package ru.practicum.android.diploma.ui.extensions

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.salary.Salary
import ru.practicum.android.diploma.util.CurrencyUtils
import java.text.NumberFormat
import java.util.Locale

fun Salary.format(context: Context): String {
    return when (this) {
        is Salary.From -> context.getString(
            R.string.salary_from,
            formatSalaryAmount(amount),
            CurrencyUtils.getCurrencySymbol(currency)
        )
        is Salary.Fixed -> context.getString(
            R.string.salary_fixed,
            formatSalaryAmount(amount),
            CurrencyUtils.getCurrencySymbol(currency)
        )
        is Salary.Range -> context.getString(
            R.string.salary_range,
            formatSalaryAmount(from),
            formatSalaryAmount(to),
            CurrencyUtils.getCurrencySymbol(currency)
        )
        Salary.NotSpecifies -> context.getString(R.string.salary_not_specified)
    }
}

fun formatSalaryAmount(amount: Int): String {
    return NumberFormat.getNumberInstance(Locale.getDefault()).format(amount)
}
