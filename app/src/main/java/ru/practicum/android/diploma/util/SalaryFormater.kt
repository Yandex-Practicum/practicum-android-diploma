package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R
import java.text.NumberFormat
import java.util.Locale

object SalaryFormater {
    fun formatSalary(context: Context, salaryFrom: Int?, salaryTo: Int?, currencySymbol: String?): String {
        val fromText = context.getString(R.string.salary_from)
        val toText = context.getString(R.string.salary_to)
        val fromToText = context.getString(R.string.salary_from_to)
        val numberFormat: NumberFormat = NumberFormat.getInstance(Locale("ru", "RU"))
        return when {
            salaryFrom != null && salaryTo != null -> {
                fromToText.format(numberFormat.format(salaryFrom), numberFormat.format(salaryTo), currencySymbol)
            }

            salaryFrom != null -> {
                fromText.format(numberFormat.format(salaryFrom), currencySymbol)
            }

            salaryTo != null -> {
                toText.format(numberFormat.format(salaryTo), currencySymbol)
            }

            else -> context.getString(R.string.salary_not_specified)
        }
    }
}
