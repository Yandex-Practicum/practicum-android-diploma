package ru.practicum.android.diploma.details.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.DetailsRepository
import ru.practicum.android.diploma.details.domain.DetailsInteractor
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.filter.data.model.NetworkResponse
import ru.practicum.android.diploma.search.domain.models.Vacancy
import javax.inject.Inject

class DetailsInteractorImpl@Inject constructor(
    private val repository: DetailsRepository
) : DetailsInteractor {
    
    override suspend fun addVacancyToFavorites(vacancy: Vacancy): Flow<Unit> {
       return repository.addVacancyToFavorite(vacancy)
    }

    override suspend fun removeVacancyFromFavorite(id: String): Flow<Int> {
        return repository.removeVacancyFromFavorite(id)
    }

    override suspend fun getFullVacancyInfo(id: String): Flow<NetworkResponse<VacancyFullInfo>> {
        return repository.getFullVacancyInfo(id)
    }

    override suspend fun getSimilarVacancies(id: String): Flow<NetworkResponse<List<Vacancy>>> {
        return repository.getSimilarVacancies(id)
    }
}