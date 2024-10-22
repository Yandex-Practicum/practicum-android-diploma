package ru.practicum.android.diploma.filter.industry.presentation.viewmodel.state

import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel

internal sealed interface IndustryState {
    data object Loading : IndustryState
    data class Content(val industryList: List<IndustryModel>) : IndustryState
    data object Empty : IndustryState
    data object Error : IndustryState
}
