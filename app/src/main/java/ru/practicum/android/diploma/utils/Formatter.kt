package ru.practicum.android.diploma.utils

import ru.practicum.android.diploma.search.data.dto.components.Salary
import java.text.NumberFormat

fun formattingSalary(salary: Salary?): String {
    if (salary == null) {
        return ""
    }
    val currency = salary.currency ?: ""
    return when {
        salary.from != null && salary.to == null -> "от ${formatNumber(salary.from)} $currency"
        salary.from == null && salary.to != null -> "от ${formatNumber(salary.to)} $currency"
        salary.from != null && salary.to != null -> "от ${formatNumber(salary.from)} до ${
            formatNumber(
                salary.to
            )
        } $currency"

        else -> ""
    }
}

private fun formatNumber(number: Int): String {
    return NumberFormat.getInstance().format(number)
}
