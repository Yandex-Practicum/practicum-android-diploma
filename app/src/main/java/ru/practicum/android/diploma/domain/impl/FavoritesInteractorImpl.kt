package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesInteractorImpl(
    private val repository: FavoritesRepository
) : FavoritesInteractor {
    override fun setVacancy(vacancy: Vacancy):Flow<Boolean> {
        return repository.setVacancy(vacancy = vacancy)
    }

    override fun getVacancy(id: String): Flow<Vacancy> {
        return repository.getVacancy(id = id)
    }

    override fun getAllVacancies(): Flow<List<Vacancy>> {
        return repository.getAllVacancies()
    }

    override fun checkVacancyInFavorite(id: String): Flow<Boolean> {
        return repository.checkVacanciesInFavorite(id)
    }

    override fun deleteVacancyFromFavorite(id: String): Flow<Boolean> {
        return repository.deleteVacancyFromFavorite(id)
    }
}
