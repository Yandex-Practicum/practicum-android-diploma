package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.ui.search.state.VacancyError

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val message: VacancyError) : Resource<T>
}
