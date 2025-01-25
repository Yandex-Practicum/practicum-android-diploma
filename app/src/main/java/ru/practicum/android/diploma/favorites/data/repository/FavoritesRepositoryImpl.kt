package ru.practicum.android.diploma.favorites.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteException
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.AppDatabase
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.common.domain.models.Resource
import ru.practicum.android.diploma.common.util.Converter
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteRepository
import ru.practicum.android.diploma.search.domain.model.VacancyItems

class FavoritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val gson: Gson,
    private val sharedPrefs: SharedPreferences,
    private val converter: Converter,
    private val context: Context
) : FavoriteRepository {
    override suspend fun getVacancyList(): Flow<Resource<List<VacancyItems>>>  = flow {
        try {
            val vacancyList = appDatabase.favoriteDao().getVacancyListByTime()
            emit(Resource.Success(convert(vacancyList)))
        } catch (e: SQLiteException) {
            emit(Resource.Error(R.string.list_is_not_recieved.toString())) // не удалось получить список вакансий
        }
   }

    private fun convert(vacancyList: List<VacancyEntity>) : List<VacancyItems> {
        return vacancyList.map { vacancy -> converter.map(vacancy)}
    }
}
