package ru.practicum.android.diploma.presentation.vacancy

sealed class VacancyDetailsCommand {
    data object NavigateBack : VacancyDetailsCommand()
    data class NavigateToShare(
        val url: String
    ) : VacancyDetailsCommand()
}
