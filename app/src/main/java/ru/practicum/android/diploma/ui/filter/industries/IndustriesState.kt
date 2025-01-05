package ru.practicum.android.diploma.ui.filter.industries

import ru.practicum.android.diploma.domain.models.Industry

sealed interface IndustriesState {
    data object NothingFound : IndustriesState
    data object NetworkError : IndustriesState
    data object ServerError : IndustriesState
    data class FoundIndustries(val industries: MutableList<Industry>) : IndustriesState
}
