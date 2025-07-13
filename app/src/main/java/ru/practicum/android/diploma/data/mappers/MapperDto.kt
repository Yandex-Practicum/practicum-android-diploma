package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.models.vacancies.SalaryRangeDto
import ru.practicum.android.diploma.data.models.vacancies.VacanciesDto
import ru.practicum.android.diploma.domain.models.salary.Salary
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy

fun VacanciesDto.toDomain(): Vacancy {
    return Vacancy(
        nameVacancy = name,
        alternateUrl = alternateUrl,
        id = id,
        employerName = employer?.name,
        logo = employer?.logoUrls?.logo90,
        salary = salaryRange.toDomain(),
        city = address?.city
    )
}

private fun SalaryRangeDto?.toDomain(): Salary {
    if (this == null) return Salary.NotSpecifies

    val from = this.from
    val to = this.to
    val currency = this.currency

    return if (from == null) {
        Salary.NotSpecifies
    } else if (to == null) {
        Salary.From(from, currency)
    } else if (to == from) {
        Salary.Fixed(from, currency)
    } else {
        Salary.Range(from, to, currency)
    }
}
