package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.data.dto.model.favorites.ShareData
import ru.practicum.android.diploma.domain.models.Vacancy

interface ShareRepository {
    fun getShareData(id: String): ShareData
    suspend fun isFavorite(id: String): Boolean
    suspend fun insertFavouritesVacancyEntity(vacancy: Vacancy)
}
