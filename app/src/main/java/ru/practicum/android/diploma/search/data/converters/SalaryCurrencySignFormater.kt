package ru.practicum.android.diploma.search.data.converters

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.vacancy.data.dto.SalaryDto
import java.text.DecimalFormat

class SalaryCurrencySignFormater(private val context: Context) {

    private val formatter = DecimalFormat("#,###")

    fun getStringSalary(salary: SalaryDto?): String {
        val from = context.getString(R.string.salary_from)
        val to = context.getString(R.string.salary_to)
        val currency = mapCurrencySign(salary?.currency)

        return if (salary?.to == null && salary?.from != null) {
            "$from ${formatter.format(salary.from.toInt())} $currency"
        } else if (salary?.to != null && salary.from == null) {
            "$to ${formatter.format(salary.to.toInt())} $currency"
        } else if (salary?.to != null && salary.from != null) {
            "$from ${formatter.format(salary.from.toInt())} $to ${formatter.format(salary.to.toInt())} $currency"
        } else {
            context.getString(R.string.without_salary)
        }
    }

    private fun mapCurrencySign(currencyCode: String?): String {
        return when (currencyCode) {
            "RUR", "RUB" -> context.getString(R.string.russian_ruble)
            "USD" -> context.getString(R.string.usa_dollar)
            "EUR" -> context.getString(R.string.euro)
            "KZT" -> context.getString(R.string.kazakhstani_tenge)
            "UAH" -> context.getString(R.string.ukrainian_hryvnia)
            "AZN" -> context.getString(R.string.azerbaijani_manat)
            "GEL" -> context.getString(R.string.georgian_lari)
            "KGT" -> context.getString(R.string.kyrgyzstani_som)
            null -> EMPTY_STRING
            else -> currencyCode
        }
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}
