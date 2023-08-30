package ru.practicum.android.diploma.details.ui

import ru.practicum.android.diploma.search.domain.models.VacancyFullInfoModel

sealed class DetailsScreenState {
    object Empty : DetailsScreenState()
    data class Content(val result: VacancyFullInfoModel) : DetailsScreenState()
}
