package ru.practicum.android.diploma.details.domain.api

import kotlinx.coroutines.flow.Flow

interface CheckIfVacancyIsInFavoritesUseCase {
    suspend operator fun invoke(id: String): Flow<Boolean>
}