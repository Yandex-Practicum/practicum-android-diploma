package ru.practicum.android.diploma.ui.filter.industries

sealed class IndustriesFragmentUpdate {

    data object GetIndustriesError : IndustriesFragmentUpdate()

    data class IndustriesList(
        val industries: List<ChildIndustryWithSelection>
    ) : IndustriesFragmentUpdate()

    data class FilteredIndustry(
        val industry: ChildIndustryWithSelection
    ):IndustriesFragmentUpdate()
}
