package ru.practicum.android.diploma.utils

import android.content.Context
import ru.practicum.android.diploma.R
import java.text.NumberFormat

fun formattingSalary(salaryFrom: String, salaryTo: String, currency: String, context: Context): String {
    return when {
        salaryFrom.isNotEmpty() && salaryTo.isEmpty() -> context.getString(
            R.string.salary_from,
            formatNumber(salaryFrom),
            currency
        )

        salaryFrom.isEmpty() && salaryTo.isNotEmpty() -> context.getString(
            R.string.salary_to,
            formatNumber(salaryTo),
            currency
        )

        salaryFrom.isNotEmpty() && salaryTo.isNotEmpty() -> context.getString(
            R.string.salary_from_to,
            formatNumber(salaryFrom),
            formatNumber(salaryTo),
            currency
        )

        else -> context.getString(R.string.salary_not_specified)
    }
}

private fun formatNumber(number: String): String {
    return NumberFormat.getInstance().format(number.toInt())
}
