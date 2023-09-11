package ru.practicum.android.diploma.details.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.DetailsLocalInteractor
import ru.practicum.android.diploma.details.domain.DetailsLocalRepository
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import javax.inject.Inject

class DetailsLocalInteractorImpl @Inject constructor(
    private val repository: DetailsLocalRepository
) : DetailsLocalInteractor {
    override suspend fun addVacancyToFavorites(vacancy: VacancyFullInfo): Flow<Unit> {
        return repository.addVacancyToFavorite(vacancy)
    }

    override suspend fun removeVacancyFromFavorite(id: String): Flow<Int> {
        return repository.removeVacancyFromFavorite(id)
    }

    override suspend fun showIfInFavourite(id: String): Flow<Boolean> {
        return repository.showIfInFavourite(id)
    }
}