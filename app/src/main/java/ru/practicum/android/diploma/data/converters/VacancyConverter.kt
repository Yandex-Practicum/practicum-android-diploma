package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.responseUnits.Salary
import ru.practicum.android.diploma.data.dto.responseUnits.VacancyDto
import ru.practicum.android.diploma.domain.models.main.Vacancy

object VacancyConverter {
    fun VacancyDto.toVacancy(): Vacancy {
        return Vacancy(
            id = id,
            name = name,
            area = area.name,
            employer = employer.name,
            salary = formatSalary(salary),
            employerImgUrl = employer.logoUrls?.medium
        )
    }

    private fun formatSalary(salary: Salary?): String {
        if (salary == null) return "Зарплата не указана"

        val currency = when (salary.currency) {
            "RUR" -> "₽"
            "USD" -> "$"
            "KZT" -> "₸"
            else -> ""
        }

        val stringBuilder = StringBuilder()

        salary.from?.let {
            stringBuilder.append("от ${salary.from} ")
        }

        salary.to?.let {
            stringBuilder.append("до ${salary.to} ")
        }
        stringBuilder.append("$currency")

        return stringBuilder.toString()
    }
}
