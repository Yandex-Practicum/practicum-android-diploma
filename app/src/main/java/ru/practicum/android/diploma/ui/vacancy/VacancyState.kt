package ru.practicum.android.diploma.ui.vacancy

import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto

sealed interface VacancyState {

    object Loading : VacancyState

    data class Content(
        val item: VacancyFullItemDto,
    ) : VacancyState

    object Empty : VacancyState

    object NetworkError: VacancyState

    object BadRequest: VacancyState

    object ServerError: VacancyState

}
