package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.data.dto.models.VacancyDto
import ru.practicum.android.diploma.domain.models.Vacancy

fun convertVacancyDtoToVacancy(vacancyDto: VacancyDto): Vacancy {
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