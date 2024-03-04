package ru.practicum.android.diploma.presentation.filters

import ru.practicum.android.diploma.domain.models.Area

sealed interface FiltersAreasState {
    object Loading : FiltersAreasState
    data class Content(val countries: List<Area>) : FiltersAreasState
    object Error : FiltersAreasState
    object Empty : FiltersAreasState

}
