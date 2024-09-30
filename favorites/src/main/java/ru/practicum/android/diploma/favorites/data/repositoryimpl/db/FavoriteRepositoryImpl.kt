package ru.practicum.android.diploma.favorites.data.repositoryimpl.db

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.favorites.data.mappers.VacancyDbMapper
import ru.practicum.android.diploma.favorites.domain.model.FavoriteVacancy
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteRepository

class FavoriteRepositoryImpl(
    private val dataBase: AppDatabase
) : FavoriteRepository {

    override fun getVacancies(): Flow<List<FavoriteVacancy>> = flow {
        emit(
            dataBase.favoriteVacancyDao().getVacancies().map { vacancy ->
                VacancyDbMapper.map(vacancy)
            }
        )
    }
}
