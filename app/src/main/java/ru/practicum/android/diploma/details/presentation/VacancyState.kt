package ru.practicum.android.diploma.details.presentation

import ru.practicum.android.diploma.details.domain.models.VacancyDetails

sealed class VacancyState{
    class Content(val vacancyDetails: VacancyDetails): VacancyState()
    class Error( val errorMessage: String, ): VacancyState()
}