package ru.practicum.android.diploma.filter.domain.model

sealed interface RegionViewState {
    data class Success(val areas: List<Area>) : RegionViewState
    data object NotFoundError : RegionViewState
    data object ServerError : RegionViewState
    data object ConnectionError : RegionViewState
    data object Loading : RegionViewState
}
