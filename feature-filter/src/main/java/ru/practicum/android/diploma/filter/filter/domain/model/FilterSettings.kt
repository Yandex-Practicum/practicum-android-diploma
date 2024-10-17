package ru.practicum.android.diploma.filter.filter.domain.model

data class FilterSettings(
    var placeSettings: PlaceSettings?,
    var branchOfProfession: IndustrySetting?,
    var expectedSalary: String?,
    var doNotShowWithoutSalary: Boolean,
)
