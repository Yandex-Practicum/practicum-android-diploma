package ru.practicum.android.diploma.data.db.converter

import android.util.Log
import ru.practicum.android.diploma.data.db.entity.FavouritesVacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyConverter {

    fun mapVacancyToEntity(vacancy: Vacancy): FavouritesVacancyEntity {
        return FavouritesVacancyEntity(
            id = vacancy.id,
            uriPicture = vacancy.employerLogoUrl,
            name = vacancy.titleOfVacancy,
            employer = vacancy.employerName,
            salary = vacancy.salary,
            isFavorite = vacancy.isFavorite
        )
    }
    fun mapEntityToVacancy(vacancyEntity: FavouritesVacancyEntity): Vacancy {
        return Vacancy(
            id = vacancyEntity.id,
            titleOfVacancy = vacancyEntity.name,
            regionName = null,
            employerName = vacancyEntity.employer,
            salary = vacancyEntity.salary,
            employerLogoUrl = vacancyEntity.uriPicture,
            isFavorite = true
        )
    }
}
