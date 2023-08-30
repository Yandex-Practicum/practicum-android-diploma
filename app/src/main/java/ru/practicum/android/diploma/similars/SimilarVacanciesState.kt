package ru.practicum.android.diploma.similars

import ru.practicum.android.diploma.search.domain.models.Vacancy

/** Состояние экрана похожих вакансий */
sealed interface SimilarVacanciesState    {
    object Empty: SimilarVacanciesState
    data class Content(val list: List<Vacancy>): SimilarVacanciesState
}