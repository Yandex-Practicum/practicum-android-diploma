package ru.practicum.android.diploma.details.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.DetailsRepository
import ru.practicum.android.diploma.details.domain.api.AddVacancyToFavoritesUseCase
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import javax.inject.Inject

class AddVacancyToFavoritesUseCaseImpl@Inject constructor(private val repository: DetailsRepository): AddVacancyToFavoritesUseCase {
    override suspend fun invoke(vacancy: VacancyFullInfo): Flow<Unit> {
        return repository.addVacancyToFavorite(vacancy)
    }
}