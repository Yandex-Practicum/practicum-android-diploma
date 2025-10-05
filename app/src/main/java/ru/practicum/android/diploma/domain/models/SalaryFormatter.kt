package ru.practicum.android.diploma.domain.models

import java.util.Locale

object SalaryFormatter {
    fun getFormattedSalary(salary: Salary): String {
        return when {
            salary.from != null && salary.to != null ->
                "от ${formatNumber(salary.from)} до ${formatNumber(salary.to)} ${salary.currency}"
            salary.from != null ->
                "от ${formatNumber(salary.from)} ${salary.currency}"
            salary.to != null ->
                "до ${formatNumber(salary.to)} ${salary.currency}"
            else -> "Зарплата не указана"
        }.trim()
    }

    private fun formatNumber(number: Int): String {
        return String.format(Locale.getDefault(), "%,d", number).replace(',', ' ')
    }
}
