package ru.practicum.android.diploma.utils

import android.content.Context
import ru.practicum.android.diploma.R
import java.text.NumberFormat

fun formattingSalary(salaryFrom: Int?, salaryTo: Int?, currency: String, context: Context): String {
    return when {
        salaryFrom != null && salaryTo == null -> context.getString(
            R.string.salary_from,
            formatNumber(salaryFrom),
            currency
        )

        salaryFrom == null && salaryTo != null -> context.getString(
            R.string.salary_to,
            formatNumber(salaryTo),
            currency
        )

        salaryFrom != null && salaryTo != null -> context.getString(
            R.string.salary_from_to,
            formatNumber(salaryFrom),
            formatNumber(salaryTo),
            currency
        )

        else -> context.getString(R.string.salary_not_specified)
    }
}

private fun formatNumber(number: Int): String {
    return NumberFormat.getInstance().format(number)
}
