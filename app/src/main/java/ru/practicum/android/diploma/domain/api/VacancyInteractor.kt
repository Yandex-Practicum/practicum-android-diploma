package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.data.dto.model.favorites.ShareData
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacancyInteractor {
    fun getShareData(id: String): ShareData
    suspend fun isFavorite(vacancyId: String): Boolean
    suspend fun addVacancyToFavorites(vacancy: Vacancy)
    suspend fun deleteFavouritesVacancyEntity(vacancy: Vacancy)
}
