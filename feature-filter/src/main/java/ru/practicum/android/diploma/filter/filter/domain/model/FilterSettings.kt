package ru.practicum.android.diploma.filter.filter.domain.model

data class FilterSettings(
    val placeSettings: PlaceSettings?,
    val branchOfProfession: IndustrySetting?,
    val expectedSalary: String?,
    val doNotShowWithoutSalary: Boolean,
) {
    companion object {
        fun emptyFilterSettings(): FilterSettings {
            return FilterSettings(
                placeSettings = PlaceSettings(null, null, null, null),
                branchOfProfession = IndustrySetting(null, null),
                expectedSalary = "",
                doNotShowWithoutSalary = false
            )
        }
    }
}
