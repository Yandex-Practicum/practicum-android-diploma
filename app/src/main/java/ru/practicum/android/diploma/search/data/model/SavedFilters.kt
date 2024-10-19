package ru.practicum.android.diploma.search.data.model

import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.filters.industries.domain.models.Industry

data class SavedFilters(
    val savedArea: Area?,
    val savedIndustry: Industry?,
    val savedCurrency: String?,
    val savedSalary: String?,
    val savedIsShowWithSalary: Boolean?
)
