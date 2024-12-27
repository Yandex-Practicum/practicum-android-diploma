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
                "${formatNumber.format(item.from).replace(',', ' ')} $codeSalary"
            }

            item.from != null && item.to == null -> {
                "от ${formatNumber.format(item.from).replace(',', ' ')} $codeSalary"
            }

            item.from == null && item.to != null -> {
                "до ${formatNumber.format(item.to).replace(',', ' ')} $codeSalary"
            }

            else -> {
                "от ${formatNumber.format(item.from ?: 0).replace(',', ' ')} $codeSalary " +
                    "до ${formatNumber.format(item.to ?: 0).replace(',', ' ')} $codeSalary"
            }
        }
    }
}
