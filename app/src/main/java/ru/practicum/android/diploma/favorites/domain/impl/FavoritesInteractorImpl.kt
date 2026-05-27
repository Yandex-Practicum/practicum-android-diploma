package ru.practicum.android.diploma.favorites.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.favorites.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.favorites.domain.repository.FavoritesRepository
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetails

class FavoritesInteractorImpl(val repository: FavoritesRepository) : FavoritesInteractor {
    override suspend fun add(details: VacancyDetails) {
        repository.add(details)
    }
    override suspend fun remove(id: String) {
        repository.remove(id)
    }
    override fun getAll(): Flow<List<Vacancy>> {
        return repository.getAll()
    }
    override fun getById(id: String): Flow<VacancyDetails?> {
        return repository.getById(id)
    }
    override fun isFavorite(id: String): Flow<Boolean> {
        return repository.isFavorite(id)
    }
}
