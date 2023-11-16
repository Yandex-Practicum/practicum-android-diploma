package ru.practicum.android.diploma.presentation

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.util.AZN
import ru.practicum.android.diploma.util.BYR
import ru.practicum.android.diploma.util.EUR
import ru.practicum.android.diploma.util.GEL
import ru.practicum.android.diploma.util.KGS
import ru.practicum.android.diploma.util.KZT
import ru.practicum.android.diploma.util.RUR
import ru.practicum.android.diploma.util.UAH
import ru.practicum.android.diploma.util.USD
import ru.practicum.android.diploma.util.UZS
import java.text.DecimalFormat

class SalaryPresenter(private val context: Context) {

    private val salaryFormat by lazy { DecimalFormat("#,###") }

    fun showSalary(salary: Salary?): String {
        if (salary == null || salary.to == null && salary.from == null)
            return context.getString(R.string.no_salary)
        var textSalary = ""
        if (salary.from != null) textSalary += String.format(
            context.getString(R.string.from),
            formatter(salary.from)
        )
        if (salary.to != null) textSalary += String.format(
            context.getString(R.string.to),
            formatter(salary.to)
        )
        textSalary += getSymbol(salary.currency)
        return textSalary
    }

    private fun getSymbol(currency: String?): String? {

        return when (currency) {
            "AZN" -> AZN
            "BYR" -> BYR
            "EUR" -> EUR
            "GEL" -> GEL
            "KGS" -> KGS
            "KZT" -> KZT
            "RUR" -> RUR
            "UAH" -> UAH
            "USD" -> USD
            "UZS" -> UZS
            else -> null
        }
    }

    private fun formatter(n: Int): String =
        salaryFormat
            .format(n)
            .replace(",", " ")

}