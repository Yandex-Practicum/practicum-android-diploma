package ru.practicum.android.diploma.data.converters

import android.icu.text.DecimalFormat
import ru.practicum.android.diploma.data.dto.responseUnits.Salary
import ru.practicum.android.diploma.data.dto.responseUnits.VacancyDto
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.KeySkillVacancyDetail
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.Phones
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDtoResponse
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.detail.VacancyDetail

object VacancyConverter {
    fun VacancyDto.toVacancy(): Vacancy {
        return Vacancy(
            id = id,
            name = name,
            area = area.name,
            employer = employer.name,
            salary = formatSalary(salary),
            employerImgUrl = employer.logoUrls?.art240
        )
    }

    fun formatSalary(salary: Salary?): String {
        if (salary == null) return "Зарплата не указана"

        val currency = when (salary.currency) {
            "RUR" -> "₽"
            "EUR" -> "€"
            "KZT" -> "₸"
            "AZN" -> "\u20BC"
            "USD" -> "$"
            "BYR" -> "\u0042\u0072"
            "GEL" -> "\u20BE"
            "UAH" -> "\u20b4"
            "UZS" -> "Soʻm"
                else -> ""
        }

        val stringBuilder = StringBuilder()

        salary.from?.let {
            stringBuilder.append("от ${formatSalary(salary.from)} ")
        }

        salary.to?.let {
            stringBuilder.append("до ${formatSalary(salary.to)} ")
        }
        stringBuilder.append("$currency")

        return stringBuilder.toString()
    }

    private fun formatSalary(salary: Int): String {
        val df = DecimalFormat()
        df.isGroupingUsed = true
        df.groupingSize = 3

        return df.format(salary)
    }
}
