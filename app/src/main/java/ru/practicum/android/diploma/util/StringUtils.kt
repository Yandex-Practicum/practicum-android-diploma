package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R

object StringUtils {
    fun getSalary(salaryFrom: String, salaryTo: String, currency: String, context: Context): String {
        return if (salaryFrom.isNotEmpty() && salaryTo.isNotEmpty()) {
            context.getString(
                R.string.tv_salary_from_to_info,
                salaryFrom,
                salaryTo,
                getCurrencySymbol(currency)
            )
        } else if (salaryFrom.isNotEmpty()) {
            context.getString(
                R.string.tv_salary_from_info,
                salaryFrom,
                getCurrencySymbol(currency)
            )
        } else if (salaryTo.isNotEmpty()) {
            context.getString(
                R.string.tv_salary_to_info,
                salaryTo,
                getCurrencySymbol(currency)
            )
        } else {
            context.getString(R.string.tv_salary_no_info)
        }
    }

    fun getVacancyTitle(name: String, city: String, context: Context): String {
        return if (city.isNotEmpty()) {
            context.getString(R.string.vacancy_name, name, city)
        } else {
            name
        }
    }

    private fun getCurrencySymbol(currency: String): String {
        return when (currency) {
            "AZN" -> "\u20bc"
            "BYR" -> "Br"
            "EUR" -> "\u20ac"
            "GEL" -> "\u20be"
            "KGS" -> "Som"
            "KZT" -> "\u20b8"
            "RUR" -> "\u20bd"
            "UAH" -> "\u20b4"
            "USD" -> "\u0024"
            "UZS" -> "сўм"
            else -> {
                ""
            }
        }
    }
}
