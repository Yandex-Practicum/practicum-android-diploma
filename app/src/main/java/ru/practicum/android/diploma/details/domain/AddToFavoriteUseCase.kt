package ru.practicum.android.diploma.details.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.Vacancy
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(private val repository: DetailsRepository) {
    suspend operator fun invoke(vacancy: Vacancy): Flow<Long>{
        return repository.addVacancyToFavorite(vacancy)
    }
}