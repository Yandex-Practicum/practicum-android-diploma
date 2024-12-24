package ru.practicum.android.diploma.data.db.converter

import ru.practicum.android.diploma.data.db.entity.FavouritesVacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyConverter {
    fun mapVacancyToEntity(vacancy: Vacancy): FavouritesVacancyEntity {
        return FavouritesVacancyEntity(
            id = vacancy.id,
            uriPicture = vacancy.employerLogoUrl,
            name = vacancy.titleOfVacancy,
            employer = vacancy.employerName,
            salary = vacancy.salary
        )
    }
}
