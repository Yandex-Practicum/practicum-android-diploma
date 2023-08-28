package ru.practicum.android.diploma.details.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.DetailsRepository
import ru.practicum.android.diploma.details.domain.DetailsInteractor
import ru.practicum.android.diploma.search.domain.Vacancy
import javax.inject.Inject

class DetailsInteractorImpl@Inject constructor(private val repository: DetailsRepository): DetailsInteractor {
    override suspend fun addVacancyToFavorites(vacancy: Vacancy): Flow<Long> {
       return repository.addVacancyToFavorite(vacancy)
    }

    override suspend fun removeVacancyFromFavorite(id: Long): Flow<Int> {
        return repository.removeVacancyFromFavorite(id)
    }

}