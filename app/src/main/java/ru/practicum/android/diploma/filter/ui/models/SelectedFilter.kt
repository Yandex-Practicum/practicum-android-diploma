package ru.practicum.android.diploma.filter.ui.models

import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Region

data class SelectedFilter(
    val country: Country? = null,
    val region: Region? = null,
    val salary: String? = null,
    val visibility: Boolean? = null,
)