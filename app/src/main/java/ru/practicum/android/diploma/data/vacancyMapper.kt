package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.FullVacancyDto
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyMapper {
    fun mapVacancyFromDto(vacancyDto: VacancyDto, foundValue: Int): Vacancy {
        return Vacancy(
            vacancyDto.id,
            vacancyDto.name,
            vacancyDto.area.name,
            vacancyDto.employer.name,
            found = foundValue,
            vacancyDto.employer.logo_urls?.original,
            Salary(
                vacancyDto.salary?.currency,
                vacancyDto.salary?.from,
                vacancyDto.salary?.to
            ),
        )
    }

    fun mapVacancyFromFullVacancyDto(vacancyDto: FullVacancyDto): Vacancy {
        return Vacancy(
            vacancyDto.id,
            vacancyDto.name,
            vacancyDto.area.name,
            vacancyDto.employer.name,
            1,
            vacancyDto.employer.logo_urls?.original,
            Salary(
                vacancyDto.salary?.currency,
                vacancyDto.salary?.from,
                vacancyDto.salary?.to
            ),
        )
    }
}
