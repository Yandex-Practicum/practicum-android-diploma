package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.converter.VacancyConverter
import ru.practicum.android.diploma.data.dto.model.favorites.ShareData
import ru.practicum.android.diploma.domain.api.ShareRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class ShareRepositoryImpl(
    id: String,
    private val appDatabase: AppDatabase,
    private val vacancyConvertor: VacancyConverter
) : ShareRepository {
    override fun getShareData(id: String): ShareData {
        return ShareData(
            url = "https://api.hh.ru/vacancies/$id"
        )
    }

    override suspend fun isFavorite(id: String): Boolean {
        return appDatabase.favouritesVacancyDao().isFavorite(vacancyId = id)
    }

    override suspend fun insertFavouritesVacancyEntity(vacancy: Vacancy) {
        appDatabase.favouritesVacancyDao().insertFavouritesVacancyEntity(vacancyConvertor.mapVacancyToEntity(vacancy))
    }
}
