package ru.practicum.android.diploma.domain.vacancy

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto
import ru.practicum.android.diploma.data.dto.model.favorites.ShareData
import ru.practicum.android.diploma.data.vacancy.VacancyRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

class VacancyInteractorImpl(private val repository: VacancyRepository) : VacancyInteractor {

    override fun getVacancyId(id: String): Flow<Pair<VacancyFullItemDto?, String?>> {
        return repository.getVacancyId(id).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override suspend fun getVacancyById(id: String) {
        repository.getVacancyById(id)
    }

    override fun getShareData(id: String): ShareData {
        return repository.getShareData(id)
    }

    override suspend fun isFavorite(id: String): Boolean {
        return repository.isFavorite(id)
    }

    override suspend fun addVacancyToFavorites(id: String) {
        repository.insertFavouritesVacancyEntity(id)
    }

    override suspend fun deleteFavouritesVacancyEntity(id: String) {
        repository.deleteFavouritesVacancyEntity(id)
    }
}
