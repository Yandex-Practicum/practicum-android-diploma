package ru.practicum.android.diploma.presentation.details

import ru.practicum.android.diploma.domain.models.VacancyDetail

sealed interface VacancyDetailsUiState {
    object Loading : VacancyDetailsUiState
    
    data class Success(val vacancy: VacancyDetail) : VacancyDetailsUiState
    
    enum class ErrorType {
        NO_INTERNET,
        SERVER_ERROR,
        NOT_FOUND
    }
    
    data class Error(val errorType: ErrorType) : VacancyDetailsUiState
}
