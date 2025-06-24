package ru.practicum.android.diploma.ui.filter.place.models

sealed interface PlaceState {
    data class Content(
        val country: Country?,
        val region: Region?
    ) : PlaceState

    data class Save(
        val country: Country?,
        val region: Region?
    ) : PlaceState

    data class ResponseRegion(
        val country: Country?
    ) : PlaceState
}
