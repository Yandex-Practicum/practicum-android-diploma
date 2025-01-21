package ru.practicum.android.diploma.search.domain.model

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

data class Salary(
    val from: Int? = null,
    val to: Int? = null,
    val currency: String,
) {
    fun getSalaryToString(): String {
        return when {
            from != null && to != null -> String.format(
                "от %s до %s%s",
                formatSalary(from),
                formatSalary(to),
                formatCurrency(currency)
            )
            from != null -> String.format(
                "от %s%s",
                formatSalary(from),
                formatCurrency(currency)
            )
            to != null -> String.format(
                "до %s%s",
                formatSalary(to),
                formatCurrency(currency)
            )
            else -> "Зарплата не указана"
        }
    }

    private fun formatSalary(salary: Int?): String {
        if (salary == null) return ""

        val dfs = DecimalFormatSymbols().apply {
            groupingSeparator = ' '
        }
        val df = DecimalFormat("###,###", dfs)
        return df.format(salary)
    }

    // java.util.Currency не поддерживает большинство символов, поэтому метод написан вручную
    private fun formatCurrency(abbr: String): String {
        return when (abbr) {
            "AZN" -> "₼"
            "BYR" -> "Br"
            "EUR" -> "€"
            "GEL" -> "₾"
            "KGS" -> "С"
            "KZT" -> "₸"
            "RUR" -> "₽"
            "UAH" -> "₴"
            "USD" -> "$"
            "UZS" -> "UZS"
            else -> ""
        }
    }
}
