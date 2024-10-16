package ru.practicum.android.diploma.filters.areas.ui.model

import ru.practicum.android.diploma.filters.areas.domain.models.Area

sealed class AreaSelectScreenState {
    data class ChooseItem(val items: List<Area>) : AreaSelectScreenState()
    data object ServerError : AreaSelectScreenState()
    data object NetworkError : AreaSelectScreenState()
    data object Empty : AreaSelectScreenState()
}
