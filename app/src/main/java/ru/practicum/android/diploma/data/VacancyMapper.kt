package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.VacanciesResponseDto
import ru.practicum.android.diploma.data.dto.VacancyCardDto
import ru.practicum.android.diploma.data.dto.VacancyCardSalaryDto
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.models.Vacancy

internal fun VacanciesResponseDto.toDomain(): VacanciesSearchResult = VacanciesSearchResult(
    found = found,
    page = page,
    pages = pages,
    vacancies = items.map { it.toDomain() },
)

private fun VacancyCardDto.toDomain(): Vacancy = Vacancy(
    id = id,
    name = name,
    company = company,
    city = city,
    salary = salary?.toDomain(),
    logo = logo,
)

private fun VacancyCardSalaryDto.toDomain(): Salary = Salary(
    from = from,
    to = to,
    currency = currency,
)
