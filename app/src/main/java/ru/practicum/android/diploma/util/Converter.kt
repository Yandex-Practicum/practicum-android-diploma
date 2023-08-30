package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.search.domain.models.Vacancy

fun convertVacancyDtoToVacancy(vacancyDto: ru.practicum.android.diploma.search.data.dto.VacancyDto): Vacancy {
    return Vacancy(
        id = vacancyDto.id,
        name = vacancyDto.name,
        city = vacancyDto.city,
        employerName = vacancyDto.employerName,
        employerLogoUrl = vacancyDto.employerLogoUrl,
        salaryCurrency = vacancyDto.salaryCurrency,
        salaryFrom = vacancyDto.salaryFrom,
        salaryTo = vacancyDto.salaryTo
    )
}