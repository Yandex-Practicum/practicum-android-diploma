package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetails

interface FavoriteInteractor {
    suspend fun getAllFavoriteVacancies() : Flow<VacancyDetails>
}

