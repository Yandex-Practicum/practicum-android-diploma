package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.VacanciesResponseDto
import ru.practicum.android.diploma.data.dto.VacancyCardDto
import ru.practicum.android.diploma.data.dto.VacancyCardSalaryDto
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancySalary
import ru.practicum.android.diploma.domain.models.VacancySearchRequest
import ru.practicum.android.diploma.domain.models.VacancySearchResult

fun VacancySearchRequest.toQueryMap(): Map<String, String> = buildMap {
    text.takeIf(String::isNotBlank)?.let { put(TEXT_QUERY_KEY, it) }
    area?.let { put(AREA_QUERY_KEY, it.toString()) }
    industry?.let { put(INDUSTRY_QUERY_KEY, it.toString()) }
    salary?.let { put(SALARY_QUERY_KEY, it.toString()) }
    put(PAGE_QUERY_KEY, page.toString())
    onlyWithSalary?.let { put(ONLY_WITH_SALARY_QUERY_KEY, it.toString()) }
}

fun VacanciesResponseDto.toDomain(): VacancySearchResult {
    return VacancySearchResult(
        found = found,
        pages = pages,
        page = page,
        vacancies = items.map(VacancyCardDto::toDomain),
    )
}

private fun VacancyCardDto.toDomain(): Vacancy {
    return Vacancy(
        id = id,
        name = name,
        company = company,
        city = city,
        salary = salary?.toDomain(),
        logo = logo,
    )
}

private fun VacancyCardSalaryDto.toDomain(): VacancySalary {
    return VacancySalary(
        from = from,
        to = to,
        currency = currency,
    )
}

private const val AREA_QUERY_KEY = "area"
private const val INDUSTRY_QUERY_KEY = "industry"
private const val TEXT_QUERY_KEY = "text"
private const val SALARY_QUERY_KEY = "salary"
private const val PAGE_QUERY_KEY = "page"
private const val ONLY_WITH_SALARY_QUERY_KEY = "only_with_salary"
