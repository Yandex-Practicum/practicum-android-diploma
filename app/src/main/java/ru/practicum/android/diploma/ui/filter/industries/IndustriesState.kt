package ru.practicum.android.diploma.ui.filter.industries

import ru.practicum.android.diploma.domain.models.Industry

sealed interface IndustriesState {
    data object NothingFound : IndustriesState
    data class Error(val errorMessage: String) : IndustriesState
    data object Loading : IndustriesState
    data class FoundIndustries(val industries: MutableList<Industry>) : IndustriesState
}
