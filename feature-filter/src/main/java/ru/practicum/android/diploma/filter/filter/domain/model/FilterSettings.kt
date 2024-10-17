package ru.practicum.android.diploma.filter.filter.domain.model

data class FilterSettings(
    val placeSettings: PlaceSettings?,
    val branchOfProfession: IndustrySetting?,
    val expectedSalary: String?,
    val doNotShowWithoutSalary: Boolean,
)
