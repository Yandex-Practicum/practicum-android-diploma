package ru.practicum.android.diploma.ui.filter.model

import ru.practicum.android.diploma.ui.filter.place.models.Country
import ru.practicum.android.diploma.ui.filter.place.models.Region

data class SelectedFilters(
    val country: Country?,
    val region: Region?,
    val industryId: String?,
    val industry: String?,
    val salary: Int?,
    val onlyWithSalary: Boolean
)
