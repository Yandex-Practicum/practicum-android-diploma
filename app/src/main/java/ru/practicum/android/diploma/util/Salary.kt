package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.data.dto.model.SalaryDto
import ru.practicum.android.diploma.domain.models.SalaryRange
import java.text.DecimalFormat

class Salary {
    private val currencyCodeMapping = mapOf(
        "AZN" to "₼",
        "BYR" to "Br",
        "EUR" to "€",
        //   "GEL" to "₾",
        //   "KGS" to "⃀",
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
        return when (val salaryRange = SalaryItem().salaryDetermine(item)) {
            is SalaryRange.NotSpecified -> "Зарплата не указана"
            is SalaryRange.SingleValue -> formatSalary(salaryRange.amount, formatNumber, codeSalary)
            is SalaryRange.FromValue -> "от ${formatSalary(salaryRange.from, formatNumber, codeSalary)}"
            is SalaryRange.ToValue -> "до ${formatSalary(salaryRange.to, formatNumber, codeSalary)}"
            is SalaryRange.Range -> "от ${formatSalary(salaryRange.from, formatNumber, codeSalary)} " +
                "до ${formatSalary(salaryRange.to, formatNumber, codeSalary)}"
        }
    }

    private fun formatSalary(amount: Number, formatNumber: DecimalFormat, codeSalary: String?): String {
        return "${formatNumber.format(amount).replace(',', ' ')} $codeSalary"
    }
}
