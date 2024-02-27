package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.responseUnits.VacancyDto
import ru.practicum.android.diploma.domain.models.Vacancy

object VacancyConverter {
    fun VacancyDto.toVacancy(): Vacancy {
        return Vacancy(
            id = id,
            name = name,
            area = area.name,
            employer = employer.name,
            salary = "от ${salary?.from} до ${salary?.to}",
            employerImgUrl = employer.logoUrls?.original ?: ""
        )
    }
}
