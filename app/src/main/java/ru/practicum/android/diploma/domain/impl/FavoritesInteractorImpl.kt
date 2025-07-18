package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.mapToEntity
import ru.practicum.android.diploma.domain.interactors.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.VacanciesPreview
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.repositories.FavoritesRepository

class FavoritesInteractorImpl(private val repository: FavoritesRepository) : FavoritesInteractor {
    override suspend fun insertVacancy(vacancy: VacancyDetails) {
        repository.insertVacancy(mapToEntity(vacancy))
    }

    override suspend fun deleteVacancy(id: String) {
        repository.deleteVacancy(id)
    }

    override fun getAllVacancies(): Flow<List<VacanciesPreview>> =
        repository.getAllVacancies()

    override suspend fun getVacancy(id: String): VacancyDetails =
        repository.getVacancy(id)

    override suspend fun isFavorite(id: String): Boolean =
        repository.isFavorite(id)
}
