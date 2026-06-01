package ru.practicum.android.diploma.domain.models

sealed interface IndustryResult {
    data class Success(val industries: List<FilterIndustry>) : IndustryResult

    data object NoInternet : IndustryResult

    data object ServerError : IndustryResult

    data object Error : IndustryResult
}
