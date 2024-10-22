package ru.practicum.android.diploma.filter.filter.domain.model

internal data class FilterSettingsModel(
    val region: String?,
    val country: String?,
    val industry: String?,
    val expectedSalary: Int?,
    val doNotShowWithoutSalary: Boolean,
)
