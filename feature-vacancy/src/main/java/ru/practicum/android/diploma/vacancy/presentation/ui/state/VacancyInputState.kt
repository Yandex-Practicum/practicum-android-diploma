package ru.practicum.android.diploma.vacancy.presentation.ui.state

sealed class VacancyInputState {
    data class VacancyNetwork(val id: String) : VacancyInputState()
    data class VacancyDb(val id: Int) : VacancyInputState()
}
