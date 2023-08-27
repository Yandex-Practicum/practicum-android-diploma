package ru.practicum.android.diploma.details.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.Vacancy
import javax.inject.Inject

class GetFavoriteVacanciesUseCase @Inject constructor(private val repository: DetailsRepository) {
     operator fun invoke(): Flow<List<Vacancy>>{
        return repository.getFavoriteVacancies()
    }
}