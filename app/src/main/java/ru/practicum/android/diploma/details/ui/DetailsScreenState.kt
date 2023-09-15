package ru.practicum.android.diploma.details.ui

import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.util.functional.Failure

sealed interface DetailsScreenState {
    
    data class Content(val vacancy: VacancyFullInfo) : DetailsScreenState
    data class Offline(val message: Failure) : DetailsScreenState
    data class Error(val message: Failure) : DetailsScreenState
    object Loading : DetailsScreenState
    object AddAnimation : DetailsScreenState
    object DeleteAnimation : DetailsScreenState
}