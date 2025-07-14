package ru.practicum.android.diploma.ui.extensions

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.salary.Salary

fun Salary.format(context: Context): String {
    return when (this) {
        is Salary.From -> context.getString(R.string.salary_from, amount, currency)
        is Salary.Fixed -> context.getString(R.string.salary_fixed, amount, currency)
        is Salary.Range -> context.getString(R.string.salary_range, from, to, currency)
        Salary.NotSpecifies -> context.getString(R.string.salary_not_specified)
    }
}
