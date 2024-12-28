package ru.practicum.android.diploma.ui.filter.settings

import ru.practicum.android.diploma.domain.models.Filter

sealed interface FilterSettingsState {

    data object Empty : FilterSettingsState
    data class FilterSettings(val filter: Filter) : FilterSettingsState

}
