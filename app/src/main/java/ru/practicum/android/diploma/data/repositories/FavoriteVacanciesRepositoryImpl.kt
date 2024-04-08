package ru.practicum.android.diploma.data.repositories

import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.domain.favorite.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

class FavoriteVacanciesRepositoryImpl(
    private val appDB: AppDatabase
) : FavoriteVacanciesRepository {

    override suspend fun insertVacancy(vacancy: Vacancy) {
        appDB.vacancyDao().insertVacancy(vacancy)
    }

}
