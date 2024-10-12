package ru.practicum.android.diploma.navigate.state

sealed interface NavigateEventState {
    data class ToVacancyDataSourceNetwork(val id: String) : NavigateEventState
    data class ToVacancyDataSourceDb(val id: Int) : NavigateEventState
    data object ToFilter : NavigateEventState
}
