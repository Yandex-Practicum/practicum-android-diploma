package ru.practicum.android.diploma.navigate.state

sealed class NavigateEventState {
    data class ToVacancyDataSourceNetwork(val id: String) : NavigateEventState()
    data class ToVacancyDataSourceDb(val id: Int) : NavigateEventState()
    data object ToFilter : NavigateEventState()
}
