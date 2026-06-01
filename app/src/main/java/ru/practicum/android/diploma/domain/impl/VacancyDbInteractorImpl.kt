package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.VacancyDbInteractor
import ru.practicum.android.diploma.domain.api.VacancyDbRepository
import ru.practicum.android.diploma.domain.models.VacancyDetail

class VacancyDbInteractorImpl(
    private val repository: VacancyDbRepository
) : VacancyDbInteractor {
    override fun observeFavoriteVacancyById(id: String): Flow<VacancyDetail> {
        return repository.observeFavoriteVacancyById(id)
    }

    override fun observeFavoriteVacancies(): Flow<List<VacancyDetail>> {
        return repository.observeFavoriteVacancies()
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
