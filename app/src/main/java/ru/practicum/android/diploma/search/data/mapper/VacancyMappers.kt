package ru.practicum.android.diploma.search.data.mapper

import ru.practicum.android.diploma.data.dto.responses.SalaryDTO
import ru.practicum.android.diploma.data.dto.responses.VacanciesSearchResponse
import ru.practicum.android.diploma.data.dto.responses.VacancyDetailDTO
import ru.practicum.android.diploma.search.domain.models.Salary
import ru.practicum.android.diploma.search.domain.models.VacanciesPage
import ru.practicum.android.diploma.search.domain.models.Vacancy

fun SalaryDTO.toDomain(): Salary = Salary(
    from = from,
    to = to,
    currency = currency
)

fun VacancyDetailDTO.toDomain(): Vacancy = Vacancy(
    id = id,
    name = name,
    description = description,
    salary = salary?.toDomain(),
    employerName = employer.name,
    areaName = area.name,
    url = url
)

fun VacanciesSearchResponse.toDomain(): VacanciesPage = VacanciesPage(
    vacancies = this.vacancies.map { it.toDomain() },
    found = this.found,
    page = this.page,
    pages = this.pages,
)
