package ru.practicum.android.diploma.filter.domain.model

interface IndustryViewState {
    data class Success(val industryList: List<Industry>) : IndustryViewState
    data object NotFoundError : IndustryViewState
    data object ServerError : IndustryViewState
    data object ConnectionError : IndustryViewState
    data object Loading : IndustryViewState
}
