package ru.practicum.android.diploma.similars

import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.functional.Failure

sealed interface SimilarVacanciesState {
    object Empty : SimilarVacanciesState
    data class Content(val list: List<Vacancy>) : SimilarVacanciesState
    data class Offline(val message: Failure) : SimilarVacanciesState
    data class Error(val message: Failure) : SimilarVacanciesState
    object Loading : SimilarVacanciesState
}