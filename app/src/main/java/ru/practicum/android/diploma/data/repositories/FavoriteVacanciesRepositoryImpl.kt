package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.domain.favorite.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

class FavoriteVacanciesRepositoryImpl(
    private val appDB: AppDatabase
) : FavoriteVacanciesRepository {

    override suspend fun insertVacancy(vacancy: Vacancy) {
        //appDB.favoriteVacanciesDAO().insertVacancy(vacancy)
    }

    override suspend fun deleteVacancy(vacancyId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllVacancies(): Flow<Vacancy> {
        TODO("Not yet implemented")
    }

    override suspend fun getVacancyById(vacancyId: String): Vacancy? {
        TODO("Not yet implemented")
    }

    override suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        TODO("Not yet implemented")
    }

}
