package ru.practicum.android.diploma.presentation

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.mok.Salary
import java.text.DecimalFormat

class SalaryPresenter {

    fun showSalary(salary: Salary?): String {
        if (salary == null)
            return R.string.not_salary.toString()
        var textSalary = ""
        if (salary.to != 0) textSalary += "от  ${formatter(salary.to)} "
        if (salary.from != 0) textSalary += "до ${formatter(salary.from)} "
        when (salary.currency) {
            "RUR", "RUB" -> textSalary += "₽"
            "BYR" -> textSalary += "Br"
            "USD" -> textSalary += "$"
            "EUR" -> textSalary += "€"
            "KZT" -> textSalary += "₸"
            "UAH" -> textSalary += "₴"
            "AZN" -> textSalary += "₼"
            "UZS" -> textSalary += "Soʻm"
            "GEL" -> textSalary += "₾"
            "KGT" -> textSalary += "с"
        }
        return textSalary
    }

    private fun formatter(n: Int): String =
        DecimalFormat("#,###")
            .format(n)
            .replace(",", " ")
}