package ru.practicum.android.diploma.domain.models

import java.util.Locale

data class Salary(
    val from: Int?,
    val to: Int?,
    val currency: String?
) {
    fun getFormattedSalary(): String {
        return when {
            from != null && to != null -> "от ${formatNumber(from)} до ${formatNumber(to)} $currency"
            from != null -> "от ${formatNumber(from)} $currency"
            to != null -> "до ${formatNumber(to)} $currency"
            else -> "Зарплата не указана"
        }.trim()
    }

    private fun formatNumber(number: Int): String {
        return String.format(Locale.getDefault(), "%,d", number).replace(',', ' ')
    }
}
