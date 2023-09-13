package ru.practicum.android.diploma.details.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.DetailsRepository
import ru.practicum.android.diploma.details.domain.api.CheckIfVacancyIsInFavoritesUseCase
import javax.inject.Inject

class CheckIfVacancyIsInFavoritesUseCaseImpl@Inject constructor(private val repository: DetailsRepository): CheckIfVacancyIsInFavoritesUseCase {
    override suspend fun invoke(id: String): Flow<Boolean> {
        return repository.showIfInFavourite(id)
    }
}