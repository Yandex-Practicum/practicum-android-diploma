package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.VacancyDetail

class FavoritesInteractorImpl(
    private val repository: FavoritesRepository
) : FavoritesInteractor {
    override fun setVacancy(vacancy: VacancyDetail): Flow<Boolean> {
        return repository.setVacancy(vacancy = vacancy)
    }

    override fun getVacancy(id: String): Flow<VacancyDetail> {
        return repository.getVacancy(id = id)
    }

    override fun getAllVacancies(): Flow<List<VacancyDetail>> {
        return repository.getAllVacancies()
    }

    override fun checkVacancyInFavorite(id: String): Flow<Boolean> {
        return repository.checkVacanciesInFavorite(id)
    }

    override fun deleteVacancyFromFavorite(id: String): Flow<Boolean> {
        return repository.deleteVacancyFromFavorite(id)
    }
}
