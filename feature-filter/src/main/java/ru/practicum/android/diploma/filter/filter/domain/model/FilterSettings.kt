package ru.practicum.android.diploma.filter.filter.domain.model

import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel

internal data class FilterSettings(
    val placeSettings: PlaceSettings?,
    val branchOfProfession: IndustryModel?,
    val expectedSalary: String?,
    val doNotShowWithoutSalary: Boolean,
    val forceSearch: Boolean,
) {
    companion object {
        fun emptyFilterSettings(): FilterSettings {
            return FilterSettings(
                placeSettings = PlaceSettings(null, null, null, null),
                branchOfProfession = IndustryModel(null, null),
                expectedSalary = "",
                doNotShowWithoutSalary = false,
                forceSearch = false
            )
        }
    }
}
