package ru.practicum.android.diploma.ui.filter.workplace.region

import ru.practicum.android.diploma.domain.models.Region

sealed interface RegionsState {

    object Loading : RegionsState
    object Error : RegionsState
    data class Content(val data: List<Region>) : RegionsState
    object NotFound : RegionsState
}
