package ru.practicum.android.diploma.data.dto.model.favorites

import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDtoConverter {
    fun convertDtoToVacancy(dto: VacancyFullItemDto): Vacancy {
        return Vacancy(
            id = dto.id,
            titleOfVacancy = dto.name,
            regionName = dto.area.name,
            salary = dto.salary?.from.toString(),
            employerName = dto.employer.name ,
            employerLogoUrl = dto.employer.logoUrls?.logo240pxUrl,
            isFavorite = false
        )
    }
}
