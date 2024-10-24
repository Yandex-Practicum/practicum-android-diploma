package ru.practicum.android.diploma.search.domain.models.sp

internal data class FilterSearch(
    val placeSearch: PlaceSearch?,
    val branchOfProfession: IndustrySearch?,
    val expectedSalary: String?,
    val doNotShowWithoutSalary: Boolean,
    val forceSearch: Boolean,
)
