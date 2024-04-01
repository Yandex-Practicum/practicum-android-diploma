package ru.practicum.android.diploma.data.vacancies.mapper

import ru.practicum.android.diploma.data.vacancies.details.VacancyDetailDtoResponse
import ru.practicum.android.diploma.domain.models.VacancyDetails

object VacancyDetailsMapper {
    fun map(vacancy: VacancyDetailDtoResponse): VacancyDetails {
        return VacancyDetails(
            id = vacancy.id,
            name = vacancy.name
        )
    }
}
