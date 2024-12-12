package ru.practicum.android.diploma.data.db.converter

import ru.practicum.android.diploma.data.db.entity.FavouritesVacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyConverter {

    fun mapVacancyToEntity(vacancy: Vacancy): FavouritesVacancyEntity {
        return FavouritesVacancyEntity(
            id = vacancy.id,
            uriPickture = vacancy.area,
            name = vacancy.text,
            employer = vacancy.employer,
            salary = vacancy.salary
        )
    }
}
