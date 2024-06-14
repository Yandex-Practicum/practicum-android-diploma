package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R
import java.text.NumberFormat
import java.util.Locale

object SalaryFormater {
    fun formaterForSalary(context: Context, from: Int?, to: Int?, currency: String?): String {
        val fromOnly = context.getString(R.string.salary_from_mask)
        val toOnly = context.getString(R.string.salary_to_mask)
        val fromAndTo = context.getString(R.string.salary_full_mask)

        val numberFormat: NumberFormat = NumberFormat.getInstance(Locale("ru", "RU"))

        return when {
            from != null && to == null -> {
                String.format(fromOnly, numberFormat.format(from), currency)
            }

            from == null && to != null -> {
                String.format(toOnly, numberFormat.format(to), currency)
            }

            from != null && to != null -> {
                String.format(fromAndTo, numberFormat.format(from), numberFormat.format(to), currency)
            }

            else -> context.getString(R.string.empty_salary)
        }
    }
}
