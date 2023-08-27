package ru.practicum.android.diploma.details.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveVacancyFromFavoritesUseCase @Inject constructor(private val repository: DetailsRepository) {
    suspend operator fun invoke(id: Long): Flow<Int> {
        return repository.removeVacancyFromFavorite(id)
    }
}