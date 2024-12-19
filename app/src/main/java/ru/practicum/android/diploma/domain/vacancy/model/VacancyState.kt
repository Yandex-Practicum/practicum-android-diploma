package ru.practicum.android.diploma.domain.vacancy.model

import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto

sealed interface VacancyState {

    object Loading : VacancyState

    data class Content(
        val item: VacancyFullItemDto,
    ) : VacancyState

    object Error : VacancyState

    object Empty : VacancyState

}
