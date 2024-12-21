package ru.practicum.android.diploma.domain.vacancy

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto
import ru.practicum.android.diploma.data.dto.model.favorites.ShareData
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacancyInteractor {

    fun getVacancyId(id: String): Flow<Pair<VacancyFullItemDto?, String?>>
    fun getShareData(id: String): ShareData
    suspend fun isFavorite(id: String): Boolean
    suspend fun addVacancyToFavorites(vacancy: Vacancy)
    suspend fun deleteFavouritesVacancyEntity(id: String)
    suspend fun getFavoritesVacancies(): Flow<List<Vacancy>>
}
