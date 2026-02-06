package ru.practicum.android.diploma.ui.vacancy

import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetailsError

sealed class VacancyState {
    object Loading : VacancyState()
    data class Content(
        val vacancy: Vacancy,
        val skillsText: List<String>?,
        val salaryFormatted: String?,
        val employerAddress: String,
        val isFavorite: Boolean
    ) : VacancyState()

    data class Error(val error: VacancyDetailsError) : VacancyState()
}
