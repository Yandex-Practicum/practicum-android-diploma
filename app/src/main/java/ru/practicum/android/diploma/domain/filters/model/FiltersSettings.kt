package ru.practicum.android.diploma.domain.filters.model

data class FiltersSettings(
    var placeOfWork: String,
    var industry: String,
    var expectedSalary: String,
    var salaryOnlyCheckbox: Boolean
)
