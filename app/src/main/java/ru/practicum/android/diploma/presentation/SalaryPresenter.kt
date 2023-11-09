package ru.practicum.android.diploma.presentation

import ru.practicum.android.diploma.domain.models.Salary
import java.text.DecimalFormat

class SalaryPresenter {
    fun showSalary(salary: Salary?): String {
        if (salary == null || salary.to == null && salary.from == null)
            return "Зарплата не указана"
        var textSalary = ""
        if (salary.from != null) textSalary += "от  ${formatter(salary.from)} "
        if (salary.to != null) textSalary += "до ${formatter(salary.to)} "
        textSalary += getSymbol(salary.currency)
        return textSalary
    }

    private fun getSymbol(currency: String?): String? {

        return when (currency) {
            "AZN" -> "\u20bc"
            "BYR" -> "\u0072"
            "EUR" -> "\u20ac"
            "GEL" -> "\u20be"
            "KGS" -> "\u043b"
            "KZT" -> "\u043b"
            "RUR" -> "\u20bd"
            "UAH" -> "\u20b4"
            "USD" -> "\u0024"
            "UZS" -> "\u043b"
            else -> null
        }
    }

    private fun formatter(n: Int): String =
        DecimalFormat("#,###")
            .format(n)
            .replace(",", " ")
}