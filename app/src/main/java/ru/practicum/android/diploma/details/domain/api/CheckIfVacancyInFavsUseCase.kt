package ru.practicum.android.diploma.details.domain.api


interface CheckIfVacancyInFavsUseCase {
    suspend operator fun invoke(id: String): Boolean
}