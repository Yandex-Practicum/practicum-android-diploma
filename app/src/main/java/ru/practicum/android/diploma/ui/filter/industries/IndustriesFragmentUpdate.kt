package ru.practicum.android.diploma.ui.filter.industries

import ru.practicum.android.diploma.domain.models.industries.ChildIndustry

sealed class IndustriesFragmentUpdate {

    data object GetIndustriesError : IndustriesFragmentUpdate()

    data class IndustriesList(
        val industries: List<ChildIndustry>
    ) : IndustriesFragmentUpdate()

}
