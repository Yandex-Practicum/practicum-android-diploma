package ru.practicum.android.diploma.details.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo

interface AddVacancyToFavoritesUseCase {
    suspend operator fun invoke(vacancy: VacancyFullInfo): Flow<Unit>
}