package ru.practicum.android.diploma.details.ui

import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo

sealed interface DetailsScreenState {

    object Empty : DetailsScreenState
    data class Content(val vacancy: VacancyFullInfo) : DetailsScreenState
    data class Offline(val message: String) : DetailsScreenState
    data class Error(val message: String) : DetailsScreenState
    object Loading : DetailsScreenState
    object AddAnimation : DetailsScreenState
    object DeleteAnimation : DetailsScreenState
}