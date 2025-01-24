package ru.practicum.android.diploma.favorites.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.internal.NopCollector.emit
import ru.practicum.android.diploma.AppDatabase
import ru.practicum.android.diploma.R
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
    override suspend fun getVacancyList(): Flow<Resource<List<VacancyItems>>> {
        val list = appDatabase.favoriteDao().getVacancyListByTime()

        if (list.isEmpty())
            emit(Resource.Error(R.string.your_mediateka_is_empty.toString()))
        else
            emit(Resource.Success(convertListToList(list)))
    }

}
