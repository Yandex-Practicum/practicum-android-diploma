package ru.practicum.android.diploma.search.domain.models.sp

data class FilterSearch(
    val placeSearch: PlaceSearch?,
    val branchOfProfession: IndustrySearch?,
    val expectedSalary: String?,
    val doNotShowWithoutSalary: Boolean,
)
