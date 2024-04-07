package ru.practicum.android.diploma.ui.filter.industries

sealed class IndustriesState {
    data object GetIndustriesError : IndustriesState()

    data class IndustriesList(
        val industries: List<ChildIndustryWithSelection>
    ) : IndustriesState()

    data class FilteredIndustry(
        val industry: ChildIndustryWithSelection
    ) : IndustriesState()
}
