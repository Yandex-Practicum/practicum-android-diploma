package ru.practicum.android.diploma.data.db

import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity

class FavouriteVacancyDbConvertor {
    fun mapVacancy(vacancy: FavoriteVacancyEntity): Vacancy {
        return Vacancy(
            vacancy.id,
            vacancy.name,
            vacancy.logoUrl,
            vacancy.area,
            vacancy.salary,
            vacancy.employer,
            vacancy.description
        )
    }

    fun mapVacancy(vacancy: Vacancy): FavoriteVacancyEntity {
        return FavoriteVacancyEntity(
            vacancy.id,
            vacancy.name,
            vacancy.logoUrl,
            vacancy.area,
            vacancy.salary,
            vacancy.employer,
            vacancy.description
        )
    }
}
