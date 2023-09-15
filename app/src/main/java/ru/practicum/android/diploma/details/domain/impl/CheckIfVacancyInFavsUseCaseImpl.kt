package ru.practicum.android.diploma.details.domain.impl

import ru.practicum.android.diploma.details.domain.DetailsRepository
import ru.practicum.android.diploma.details.domain.api.CheckIfVacancyInFavsUseCase
import javax.inject.Inject

class CheckIfVacancyInFavsUseCaseImpl @Inject constructor(private val repository: DetailsRepository) : CheckIfVacancyInFavsUseCase{
    override suspend fun invoke(id: String): Boolean {
        return repository.isVacancyInFavs(id)
    }
}