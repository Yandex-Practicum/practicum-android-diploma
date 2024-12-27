package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.data.dto.model.SalaryDto
import java.text.DecimalFormat

class Salary {
    private val currencyCodeMapping = mapOf(
        "AZN" to "₼",
        "BYR" to "Br",
        "EUR" to "€",
        //   "GEL" to "₾",
        //    "KGS" to "⃀",
        "KZT" to "₸",
        "RUR" to "₽",
        "UAH" to "₴",
        "USD" to "$",
        "UZS" to "Soʻm",
    )

    private val formatNumber = DecimalFormat("#,###")
    fun salaryFun(item: SalaryDto?): String {
        if (item == null) {
            return "Зарплата не указана"
        }
        val codeSalary = currencyCodeMapping[item.currency] ?: item.currency
        return when {
            item.to == null && item.from == null -> "Зарплата не указана"

            item.from != null && item.to != null && item.from == item.to -> {
                formatSalary(item.from, formatNumber, codeSalary)
            }

            item.from != null && item.to == null -> {
                "от ${formatSalary(item.from, formatNumber, codeSalary)}"
            }

            item.from == null && item.to != null -> {
                "до ${formatSalary(item.to, formatNumber, codeSalary)}"
            }

            else -> {
                "от ${formatSalary(item.from ?: 0, formatNumber, codeSalary)} " +
                    "до ${formatSalary(item.to ?: 0, formatNumber, codeSalary)}"
            }
        }
    }

    private fun formatSalary(amount: Number, formatNumber: DecimalFormat, codeSalary: String?): String {
        return "${formatNumber.format(amount).replace(',', ' ')} $codeSalary"
    }
}
