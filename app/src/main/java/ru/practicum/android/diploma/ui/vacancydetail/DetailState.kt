package ru.practicum.android.diploma.ui.vacancydetail

import ru.practicum.android.diploma.domain.models.detail.VacancyDetail
import ru.practicum.android.diploma.domain.models.main.Vacancy

sealed interface DetailState {

    data object Loading : DetailState

    data class Content(
        val vacancyDetail : VacancyDetail
    ) : DetailState

    data class Error(
        val errorMessage: Int
    ) : DetailState
}
