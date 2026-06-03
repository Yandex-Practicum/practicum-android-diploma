package ru.practicum.android.diploma.region.ui

import ru.practicum.android.diploma.region.domain.models.RegionItem

enum class RegionError {
    NO_INTERNET,
    ERROR
}

sealed class RegionScreenState {
    object Loading : RegionScreenState()
    class Content(val regions: List<RegionItem>) : RegionScreenState()
    object Empty : RegionScreenState()
    class Error(val error: RegionError) : RegionScreenState()
}
