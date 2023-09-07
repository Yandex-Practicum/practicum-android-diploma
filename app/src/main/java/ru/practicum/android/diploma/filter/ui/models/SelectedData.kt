package ru.practicum.android.diploma.filter.ui.models

import ru.practicum.android.diploma.filter.domain.models.Country

data class SelectedData(
    val country: Country? = null,
    val region: Pair<String, String>? = null,
    val salary: String? = null,
    val visibility: Boolean? = null,
)