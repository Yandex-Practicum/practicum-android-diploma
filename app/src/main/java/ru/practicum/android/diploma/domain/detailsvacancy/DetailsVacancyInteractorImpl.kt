package ru.practicum.android.diploma.domain.detailsvacancy

import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.repositories.FavoritesRepository

class DetailsVacancyInteractorImpl(
    private val detailsVacancyRepository: DetailsVacancyRepository,
    private val favoritesRepository: FavoritesRepository
) : DetailsVacancyInteractor {
    override suspend fun doRequest(vacancyId: String): Result<VacancyDetails> {
        return when (favoritesRepository.isFavorite(vacancyId)) {
            true -> Result.success(favoritesRepository.getVacancy(vacancyId))
            false -> detailsVacancyRepository.doRequest(vacancyId)
        }
    }
}
