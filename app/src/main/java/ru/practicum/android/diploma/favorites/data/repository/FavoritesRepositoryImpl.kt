package ru.practicum.android.diploma.favorites.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteException
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.AppDatabase
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.common.domain.models.Resource
import ru.practicum.android.diploma.common.util.VacancyEntityConverter
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteRepository
import ru.practicum.android.diploma.search.domain.model.VacancyItems

class FavoritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val gson: Gson,
    private val sharedPrefs: SharedPreferences,
    private val converter: VacancyEntityConverter,
    private val context: Context
) : FavoriteRepository {
    override suspend fun getVacancyList(): Flow<Resource<List<VacancyItems>>> = flow {
        try {
            val vacancyList = appDatabase.favoriteDao().getVacancyListByTime()
            emit(Resource.Success(convert(vacancyList)))
        } catch (e: SQLiteException) {
            Log.e("Favorites", "Database error fetching vacancies", e)
            emit(Resource.Error(context.getString(R.string.no_vacancies_found_text_hint)))
        }
    }

    private fun convert(vacancyList: List<VacancyEntity>): List<VacancyItems> {
        return vacancyList.map { vacancy -> converter.map(vacancy) }
    }

    override suspend fun insertFavoriteVacancy(vacancy: VacancyItems) {
        appDatabase.favoriteDao().insertVacancy(converter.map(vacancy))
    }
}
