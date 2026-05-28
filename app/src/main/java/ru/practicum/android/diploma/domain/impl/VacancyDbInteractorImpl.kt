package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.VacancyDbInteractor
import ru.practicum.android.diploma.domain.api.VacancyDbRepository
import ru.practicum.android.diploma.domain.models.VacancyDetail

class VacancyDbInteractorImpl(
    private val repository: VacancyDbRepository
) : VacancyDbInteractor {

    override fun getFavoriteVacancies(): Flow<List<VacancyDetail>> {
        return repository.getFavoriteVacancies()
    }

    override suspend fun addVacancyToFavorites(vacancy: VacancyDetail) {
        return repository.addVacancyToFavorites(vacancy)
    }

    override suspend fun deleteVacancyFromFavorites(vacancyId: String) {
        return repository.deleteVacancyFromFavorites(vacancyId)
    }

    override suspend fun checkVacancyIsFavorite(vacancyId: String): Boolean {
        return repository.checkVacancyIsFavorite(vacancyId)
    }
}
