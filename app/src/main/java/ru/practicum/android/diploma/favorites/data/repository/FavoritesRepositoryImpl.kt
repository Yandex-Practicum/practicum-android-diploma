package ru.practicum.android.diploma.favorites.data.repository

import android.content.Context
import android.database.sqlite.SQLiteException
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.AppDatabase
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.common.domain.models.Resource
import ru.practicum.android.diploma.favorites.data.VacancyEntityMapper
import ru.practicum.android.diploma.favorites.domain.entity.VacancyFavorite
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteRepository
import ru.practicum.android.diploma.search.domain.model.VacancyItems

class FavoritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val converter: VacancyEntityMapper,
    private val context: Context
) : FavoriteRepository {
    override suspend fun getVacancyList(): Flow<Resource<List<VacancyItems>>> = flow {
        try {
            // throw SQLiteException()
            val vacancyList = appDatabase.favoriteDao().getVacancyListByTime()
            emit(Resource.Success(convert(vacancyList)))
        } catch (e: SQLiteException) {
            Log.e("Favorites", "Database error fetching vacancies", e)
            emit(Resource.Error(context.getString(R.string.no_vacancies_found_text_hint)))
        }
    }

    private fun convert(vacancyList: List<VacancyEntity>): List<VacancyItems> {
        return vacancyList.map { vacancy -> converter.convertToVacancyItems(vacancy) }
    }

    override suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        return appDatabase.favoriteDao().isVacancyFavorite(vacancyId)
    }

    override suspend fun deleteVacancyById(id: String) {
        appDatabase.favoriteDao().deleteVacancyById(id)
    }

    override fun getVacancyById(id: String): Flow<VacancyFavorite?> {
        return appDatabase.favoriteDao().getVacancyById(id)
            .map { vacancyEntity -> converter.map(vacancyEntity) }
    }

    override suspend fun insertFavoriteVacancy(vacancy: VacancyFavorite) {
        appDatabase.favoriteDao().insertVacancy(converter.map(vacancy))
    }
}
