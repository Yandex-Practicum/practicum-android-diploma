package ru.practicum.android.diploma.presentation.searchfilters.industries

import ru.practicum.android.diploma.domain.models.industries.Industry

sealed class IndustriesUiState {
    data object Loading : IndustriesUiState()
    data class Content(val industries: List<Industry>) : IndustriesUiState()
    data object Error : IndustriesUiState()
}
