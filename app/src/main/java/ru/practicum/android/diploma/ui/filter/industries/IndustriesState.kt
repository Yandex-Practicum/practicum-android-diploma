package ru.practicum.android.diploma.ui.filter.industries

import ru.practicum.android.diploma.data.dto.model.industries.IndustriesFullDto
import ru.practicum.android.diploma.domain.models.Industries

sealed interface IndustriesState {
    data object NothingFound : IndustriesState
    data class Error(val errorMessage: String) : IndustriesState
    data object Loading : IndustriesState
    data class FoundIndustries(val industries: MutableList<Industries>) : IndustriesState
}
