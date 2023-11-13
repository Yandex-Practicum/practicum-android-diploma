package ru.practicum.android.diploma.presentation

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Salary
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

    companion object {
        const val AZN = "\u20bc"
        const val BYR = "\u0072"
        const val EUR = "\u20ac"
        const val GEL = "\u20be"
        const val KGS = "\u043b"
        const val KZT = "\u043b"
        const val RUR = "\u20bd"
        const val UAH = "\u20b4"
        const val USD = "\u0024"
        const val UZS = "\u043b"
    }
}