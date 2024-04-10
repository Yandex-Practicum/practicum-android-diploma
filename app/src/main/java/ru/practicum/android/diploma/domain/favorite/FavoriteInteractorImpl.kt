package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetails

class FavoriteInteractorImpl(
    private val favoriteVacanciesRepository: FavoriteVacanciesRepository
) : FavoriteInteractor {
    override suspend fun getAllFavoriteVacancies(): Flow<VacancyDetails> {
        return favoriteVacanciesRepository.getAllVacancies()
    }
}
