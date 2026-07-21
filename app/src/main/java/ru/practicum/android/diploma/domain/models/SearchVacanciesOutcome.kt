package ru.practicum.android.diploma.domain.models

sealed interface SearchVacanciesOutcome {
    data class Success(val result: VacanciesSearchResult) : SearchVacanciesOutcome

    data object Empty : SearchVacanciesOutcome

    data object Error : SearchVacanciesOutcome
}
