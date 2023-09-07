package ru.practicum.android.diploma.filter.ui.models

data class SelectedData(
    val country: Pair<String, String>? = null,
    val region: Pair<String, String>? = null,
    val salary: String? = null,
    val visibility: Boolean? = null,
)