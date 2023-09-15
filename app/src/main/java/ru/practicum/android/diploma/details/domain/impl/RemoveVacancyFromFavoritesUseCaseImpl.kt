package ru.practicum.android.diploma.details.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.DetailsRepository
import ru.practicum.android.diploma.details.domain.api.RemoveVacancyFromFavoritesUseCase
import javax.inject.Inject

class RemoveVacancyFromFavoritesUseCaseImpl@Inject constructor(private val repository: DetailsRepository): RemoveVacancyFromFavoritesUseCase {

    override suspend fun invoke(id: String): Flow<Int> {
        return repository.removeVacancyFromFavorite(id)
    }
}