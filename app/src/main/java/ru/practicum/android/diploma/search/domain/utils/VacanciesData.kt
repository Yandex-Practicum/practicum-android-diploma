package ru.practicum.android.diploma.search.domain.utils

sealed interface VacanciesData<T> {
    data class Data<T>(val value: T) : VacanciesData<T>
    data class Error<T>(val error: VacanciesSearchError) : VacanciesData<T>

    enum class VacanciesSearchError {
        NO_INTERNET,
        CLIENT_ERROR,
        SERVER_ERROR,
    }
}
