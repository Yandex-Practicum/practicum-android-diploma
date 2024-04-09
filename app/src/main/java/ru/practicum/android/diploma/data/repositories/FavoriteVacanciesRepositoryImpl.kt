package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converter.FavoriteVacanciesConverter.convert
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.domain.favorite.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.models.VacancyDetails

class FavoriteVacanciesRepositoryImpl(
    private val appDB: AppDatabase
) : FavoriteVacanciesRepository {

    override suspend fun insertVacancy(vacancy: VacancyDetails) {
        appDB.favoriteVacanciesDao().insertVacancy(vacancy.convert())
    }

    override suspend fun deleteVacancy(vacancyId: String) {
        appDB.favoriteVacanciesDao().deleteVacancy(vacancyId)
    }

    override suspend fun getAllVacancies(): Flow<VacancyDetails> = flow {
        appDB.favoriteVacanciesDao().getAllVacancies().forEach {
            emit(it.convert())
        }
    }

    override suspend fun getVacancyById(vacancyId: String): VacancyDetails? {
        return appDB.favoriteVacanciesDao().getVacancyById(vacancyId)?.convert()
    }

    override suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        return appDB.favoriteVacanciesDao().isVacancyFavorite(vacancyId) == 1
    }

}
