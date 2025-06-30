package ru.practicum.android.diploma.ui.filter.industry

sealed interface IndustryState {
    data class CONTENT(val industryListItems: List<IndustryListItem>) : IndustryState
    object LOADING : IndustryState
    data class ERROR(val error: Int) : IndustryState
    object EMPTY : IndustryState
}
