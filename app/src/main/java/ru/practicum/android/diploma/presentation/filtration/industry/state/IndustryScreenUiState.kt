package ru.practicum.android.diploma.presentation.filtration.industry.state

import ru.practicum.android.diploma.domain.models.FilterIndustry

data class IndustryScreenUiState(
    val status: IndustryScreenState = IndustryScreenState.Initial,
    val searchQuery: String = "",
    val industries: List<FilterIndustry> = emptyList(),
    val selectedIndustry: FilterIndustry? = null,
) {
    val showButton: Boolean
        get() = selectedIndustry != null
}
