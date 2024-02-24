package ru.practicum.android.diploma.favourites.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import ru.practicum.android.diploma.core.data.mapper.VacancyMapper
import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.core.domain.model.ShortVacancy
import ru.practicum.android.diploma.favourites.data.db.AppDatabase
import ru.practicum.android.diploma.favourites.data.entity.FavoriteEntity
import ru.practicum.android.diploma.favourites.domain.api.GetFavouritesRepository

class GetFavouritesRepositoryImpl(private val appDatabase: AppDatabase) : GetFavouritesRepository {
    override suspend fun getFavouritesList(): Flow<List<DetailVacancy>> = flow {
        val vacancy = appDatabase.vacancyDao().getVacancy()
        emit(vacancy.map { VacancyMapper.mapToDetailVacancy(it) })
    }

    override suspend fun fillVacList(vac: DetailVacancy) {
        appDatabase.vacancyDao().addVacancyToFavorites(VacancyMapper.mapToFavoritesEntity(vac))
    }

}
