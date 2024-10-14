package ru.practicum.android.diploma.filters.presentation

sealed class FilterSettingsStateScreen {
    data class FilterSettings(
        val area: String,
        val industry: String,
        val salary: String,
        val showWithSalary: Boolean
    )
}
