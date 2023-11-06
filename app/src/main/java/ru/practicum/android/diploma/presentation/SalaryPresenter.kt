package ru.practicum.android.diploma.presentation

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Salary
import java.text.DecimalFormat

class SalaryPresenter {

    fun showSalary(salary: Salary?): String {
        if (salary == null)
            return R.string.not_salary.toString()
        var textSalary = ""
        if (salary.to != 0) textSalary += "от  ${salary.to?.let { formatter(it) }} "
        if (salary.from != 0) textSalary += "до ${salary.from?.let { formatter(it) }} "
        textSalary.plus(getSymbol(salary.currency))
        return textSalary
    }

    private fun getSymbol(currency: String?): String? {

        return when (currency) {
            "AZN" -> "₼"
            "BYR" -> "Br"
            "EUR" -> "€"
            "GEL" -> "₾"
            "KGS" -> "с"
            "KZT" -> "₸"
            "RUR" -> "₽"
            "UAH" -> "₴"
            "USD" -> "$"
            "UZS" -> "UZS"
            else -> null
        }
    }
    private fun formatter(n: Int): String =
        DecimalFormat("#,###")
            .format(n)
            .replace(",", " ")
}