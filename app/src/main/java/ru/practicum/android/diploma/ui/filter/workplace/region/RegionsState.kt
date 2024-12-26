package ru.practicum.android.diploma.ui.filter.workplace.region

import ru.practicum.android.diploma.domain.models.Region

sealed interface RegionsState {

    object Loading : RegionsState
    data class Error(val message: String) : RegionsState
    data class Content(val data: List<Region>) : RegionsState
}
