package ru.practicum.android.diploma.domain.models

sealed interface AreaResult {
    data class Success(val areas: List<FilterArea>) : AreaResult

    data object NoInternet : AreaResult

    data object ServerError : AreaResult

    data object Empty : AreaResult

    data object Error : AreaResult
}
